package gerenciador;

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
	
	public void CriarIndice(byte arquivo, UnidadeDescricao[] descricoes, String nome){
		throw new RuntimeException("N�o implementado");
	}
	
	public void CriarIndice(Arquivo arquivo, UnidadeDescricao[] descricoes, String nome){
		if (descricoes.length < 1){
			throw new RuntimeException("Selecione no m�nimo uma descri��o!");
		}
		if (arquivo == null){
			throw new RuntimeException("Arquivo n�o selecionado!");
		}
		
		// criar arquivo de indice
		IArquivo indice = buffer.getGAFromCache().CriarArquivo(nome, descricoes, ETipoBlocoArquivo.indices);
		
		// adicionar indice no aruqivo
		arquivo.adicionarIndice(indice.getId());
		
		// adicionarEntrada somente no indice novo

		for(int i = 1; i < arquivo.getBlocoControle().getProxBlocoLivre(); i++){
			byte containerid = arquivo.getBlocoControle().getHeader().getContainerId();
			IBloco bloco = buffer.getBloco(new RowId(containerid, i, 0));//arquivo.requisitarBloco(i);
			
			for(ITupla tupla : bloco.getDados().getTuplas()){
//				AdicionarAoIndice(indice, tupla);				
			}
		}
		
		
		// atualizar os arquivos em disco
//		arquivo.adicionarIndice(containerId)
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
		// verificar se tem valores compat�veis com indice na tupla
		// antes de inserir, seleciona-los e inserir
		BlocoControle bcontroleIndice = indice.getBlocoControle();
		Chave chave = getChave_VS_Tupla(bcontroleIndice.getDescritor(), tupla, tabela.getDescritor());
		
		if(chave != null){
			boolean primeira_entrada = bcontroleIndice.getProxBlocoLivre() == 1;
			
			if(primeira_entrada){
				// indiceids ser�o a raiz
				Node node = createNode(indice);
				bcontroleIndice.setIndices(ByteArrayTools.intToByteArray(node.getBlocoId()));
				node.addTupla(tupla);
				buffer.addBloco(indice, node);
			}else{
				Node raiz = getRaiz(indice);
				boolean adicionou = false;
				while (!adicionou){
					if(!raiz.hasChild()){
						// ent�o � folha
						if(raiz.isFull()){
							// overflow
						}else{
							raiz.addTupla(chave);
							// atualizar esse bloco no disco
						}
						adicionou = true;
					}else{
						// n�o � folha ainda
						raiz = (Node) buffer.getBloco(raiz.getSubArvore(chave));
					}
				}
				
			}				
		}else{
			log.Write(tupla.toString() + " imcompativel com o indice " + indice.getNome());
		}
			
	}
	
	private Chave getChave_VS_Tupla(Descritor descritorIndice, ITupla tupla, Descritor descritorArquivo) {
		// TODO verificar corretude do m�todo
		Coluna[] colunas = new Coluna[descritorIndice.getNumberOfColumns()];
		
		for(int i = 0; i < descritorIndice.getNumberOfColumns(); i++){
			UnidadeDescricao descricao = descritorIndice.getUnidadeDescricao(i);
			
			int indexDescricaoNaTabela = 0;
			for(int j = 0; j < descritorArquivo.getNumberOfColumns(); j++){
				if(descritorArquivo.getUnidadeDescricao(i).getNome().equals(descricao.getNome())){
					indexDescricaoNaTabela = j;
					break;
				}
			}
			
			if(indexDescricaoNaTabela == 0) return null;
			
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

	private Node getRaiz(IArquivo indice){
		// TODO verificar corretude do m�todo
		return (Node) buffer.getBloco(
				new RowId(
						indice.getId(), 
						ByteArrayTools.byteArrayToInt(indice.getBlocoControle().getIndices()),
						0));
	}
	
	private Node createNode(IArquivo indice){
		// TODO verificar corretude do m�todo
		Node node = null;
		try {
			
			node = new Node(indice.getId(), 
					indice.getBlocoControle().getProxBlocoLivre(), 
					ETipoBlocoArquivo.indices, 
					indice.getDescritor(), 
					(short) 0);
			
		} catch (IncorrectFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
}
























