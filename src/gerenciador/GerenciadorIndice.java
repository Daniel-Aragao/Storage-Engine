package gerenciador;

import java.util.ArrayList;
import java.util.Optional;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocos.Coluna;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IArquivo;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.arquivos.interfaces.ITupla;
import gerenciador.indice.blocos.Chave;
import gerenciador.indice.blocos.Node;
import gerenciador.loger.Log;
import gerenciador.utils.ByteArrayTools;

public class GerenciadorIndice {
	
	private	GerenciadorBuffer buffer;
	private ILog log;
	
	public GerenciadorIndice(){
		buffer = new GerenciadorBuffer();
		log = new Log();
	}
	
	public int CriarIndice(byte arquivo, UnidadeDescricao[] descricoes, String nome){
		return CriarIndice((Arquivo)buffer.getGAFromCache().getArquivo(arquivo), descricoes, nome);		
	}
	
	public int CriarIndice(Arquivo arquivo, UnidadeDescricao[] descricoes, String nome){
		if (descricoes.length < 1){
			throw new RuntimeException("Selecione no m�nimo uma descri��o!");
		}
		if (arquivo == null){
			throw new RuntimeException("Arquivo n�o selecionado!");
		}
		
		// criar arquivo de indice
		IArquivo indice = buffer.getGAFromCache().CriarArquivo(nome, descricoes, ETipoBlocoArquivo.indices);
		indice.setQtdIndice(
				(byte) ByteArrayTools.byteArrayToInt(indice.getBlocoControle().getIndices()));
		
		// adicionar indice no aruqivo
		arquivo.adicionarIndice(indice.getId());
		
		// adicionarEntrada somente no indice novo

		for(int i = 1; i < arquivo.getBlocoControle().getProxBlocoLivre(); i++){
			IBloco bloco = buffer.getBloco(new RowId(arquivo.getId(), i, 0));
			
			for(ITupla tupla : bloco.getDados().getTuplas()){
				AdicionarAoIndice(indice, tupla, arquivo);				
			}
		}
		
		
		// atualizar os arquivos em disco
		return 0;
	}
	
	public void AdicionarEntrada(ITupla tupla, IArquivo arquivo){
		arquivo.AdicionarLinha(tupla);
		
		if(arquivo.qtdIndices() > 0){
			for(byte indiceId : arquivo.getBlocoControle().getIndices()){
				if(indiceId > (byte)0){
					AdicionarAoIndice(buffer.getGAFromCache().getArquivo(indiceId), tupla, arquivo);
				}
			}
		}
	}
	
	private void AdicionarAoIndice(IArquivo indice, ITupla tupla, IArquivo tabela){
		BlocoControle bcontroleIndice = indice.getBlocoControle();
		Chave chave = convertTuplaIntoChave(bcontroleIndice.getDescritor(), tupla, tabela.getDescritor());
		
		if(chave != null){
			boolean primeira_entrada = bcontroleIndice.getProxBlocoLivre() == 1;
			
			if(primeira_entrada){
				// vetor indiceids ser�o a raiz
				Node node = createNode(indice);
				atualizarRaiz(bcontroleIndice, node);
				
				node.addTupla(tupla);
				buffer.addBloco(indice, node);
			}else{
				Node folha = getRaiz(indice);
				boolean adicionou = false;
				
				while (!adicionou){
					if(!folha.hasChild()){
						// ent�o � folha						
						
						folha.addTupla(chave);
						folha.ordenar(buffer);
						
						if(folha.overflow()){
							tratarOverflow(indice, folha);							
						}else{
							folha.atualizar();
						}
						
						adicionou = true;
					}else{
						// n�o � folha ainda
						folha = (Node) buffer.getBloco(folha.getSubArvore(chave));
					}
				}
				
			}				
		}else{
			log.Write(tupla.toString() + " imcompativel com o indice " + indice.getNome());
		}
			
	}
	
	private void tratarOverflow(IArquivo indice, Node folha){
		Node node = createNode(indice);
		
		ArrayList<Chave> mchaves = folha.getMetadeChaves();
		
		Chave chaveCentral = mchaves.remove(0);
		
		for(Chave c : mchaves){
			node.addTupla(c);
		}
		
		ArrayList<RowId> mponteiros = folha.getMetadePonteiros();
		for(RowId ri : mponteiros){
			node.addPonteiro(ri);
		}
		
		node.ordenar(buffer);
		
		indice.addBloco(node);
		folha.atualizar();
		
		Node pai = getPai(folha, indice);
		
		if (pai == null){
			pai = createNode(indice);
			
			pai.addPonteiro(folha.getBlocoTupleId());
			atualizarRaiz(indice.getBlocoControle(), pai);
		}
		
		pai.addTupla(chaveCentral);
		pai.addPonteiro(node.getBlocoTupleId());
		pai.ordenar(buffer);
		
		if(pai.overflow()){
			tratarOverflow(indice, pai);
		}
		pai.atualizar();
	}
	
	private Node getPai(Node folha, IArquivo indice){
		
		Node raiz = getRaiz(indice);
		if(raiz == null){
			return null;			
		}
		
		aqui
		return null;
	}
	
	private Chave convertTuplaIntoChave(Descritor descritorIndice, ITupla tupla, Descritor descritorArquivo) {
		Coluna[] colunas = new Coluna[descritorIndice.getNumberOfColumns()];
		
		for(int i = 0; i < descritorIndice.getNumberOfColumns(); i++){
			UnidadeDescricao descricao = descritorIndice.getUnidadeDescricao(i);
			
			int indexDescricaoNaTabela = -1;
			for(int j = 0; j < descritorArquivo.getNumberOfColumns(); j++){
				if(descritorArquivo.getUnidadeDescricao(i).getNome().equals(descricao.getNome())){
					indexDescricaoNaTabela = j;
					break;
				}
			}
			
			if(indexDescricaoNaTabela == -1) return null;
			
			Coluna coluna = tupla.getColuna(indexDescricaoNaTabela);
			
			colunas[i] = (coluna);
		}		
		
		try {
			return new Chave(colunas, null, descritorIndice, tupla.getTupleId());
		} catch (IncorrectFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Node getRaiz(IArquivo indice){
		return (Node) buffer.getBloco(
				new RowId(
						indice.getId(), 
						ByteArrayTools.byteArrayToInt(indice.getBlocoControle().getIndices()),
						0));
	}
	
	private Node createNode(IArquivo indice){
		Node node = null;
		try {
			
			node = new Node(indice.getId(), 
					indice.getBlocoControle().getProxBlocoLivre(), 
					ETipoBlocoArquivo.indices, 
					indice.getDescritor());
			
		} catch (IncorrectFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
	
	private void atualizarRaiz(BlocoControle bcontroleIndice, IBloco node){
		bcontroleIndice.setIndices(ByteArrayTools.intToByteArray(node.getBlocoId()));
	}
	
//	public IBloco buscar()
}
























