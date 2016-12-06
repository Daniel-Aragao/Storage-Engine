package gerenciador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
import gerenciador.indice.blocos.DadosNode;
import gerenciador.indice.blocos.HeaderNode;
import gerenciador.indice.blocos.Node;
import gerenciador.loger.Log;
import gerenciador.utils.ByteArrayTools;

public class GerenciadorIndice {

	private GerenciadorBuffer buffer;
	private ILog log;

	public GerenciadorIndice() {
		buffer = new GerenciadorBuffer();
		log = new Log();
	}

	public int CriarIndice(byte arquivo, UnidadeDescricao[] descricoes, String nome) {
		return CriarIndice((Arquivo) buffer.getGAFromCache().getArquivo(arquivo), descricoes, nome);
	}

	public int CriarIndice(Arquivo arquivo, UnidadeDescricao[] descricoes, String nome) {
		if (descricoes.length < 1) {
			throw new RuntimeException("Selecione no mínimo uma descrição!");
		}
		if (arquivo == null) {
			throw new RuntimeException("Arquivo não selecionado!");
		}
		validarDescricoes(descricoes, arquivo.getDescritor().getDescritores());

		// criar arquivo de indice
		IArquivo indice = buffer.getGAFromCache().CriarArquivo(nome, descricoes, ETipoBlocoArquivo.indices);
		indice.setQtdIndice(arquivo.getId());

		// adicionar indice no aruqivo
		arquivo.adicionarIndice(indice.getId());
		

		// adicionarEntrada somente no indice novo

		for (int i = 1; i < arquivo.getBlocoControle().getProxBlocoLivre(); i++) {
			IBloco bloco = buffer.getBloco(new RowId(arquivo.getId(), i, 0));

			for (ITupla tupla : bloco.getDados().getTuplas()) {
				AdicionarAoIndice(indice, tupla, arquivo);
			}
		}

		// ordem
		return ((HeaderNode)getRaiz(indice).getHeader()).getOrdemArvore();
	}

	private void validarDescricoes(UnidadeDescricao[] descricoes, ArrayList<UnidadeDescricao> descritores) {
		boolean todoscontidos = true;
		for(UnidadeDescricao udroes : descricoes){
			boolean contains = false;
			for(UnidadeDescricao udres : descritores){
				if(udres.getNome().equals(udroes.getNome())){
					contains = true;
					break;
				}
			}
			if(!contains){
				todoscontidos = false;
				break;
			}
		}
		
		if(!todoscontidos){
			throw new RuntimeException("Descricoes informadas não estão nos descritores da tabela");
		}
		
	}

	public void AdicionarEntrada(ITupla tupla, IArquivo arquivo) {
		arquivo.AdicionarLinha(tupla);

		if (arquivo.qtdIndices() > 0) {
			for (byte indiceId : arquivo.getBlocoControle().getIndices()) {
				if (indiceId > (byte) 0) {
					AdicionarAoIndice(buffer.getGAFromCache().getArquivo(indiceId), tupla, arquivo);
				}
			}
		}
	}

	private void AdicionarAoIndice(IArquivo indice, ITupla tupla, IArquivo tabela) {
		BlocoControle bcontroleIndice = indice.getBlocoControle();
		Chave chave = convertTuplaIntoChave(bcontroleIndice.getDescritor(), tupla, tabela.getDescritor());

		if (chave != null) {
			boolean primeira_entrada = bcontroleIndice.getProxBlocoLivre() == 1;

			if (primeira_entrada) {
				// vetor indiceids serão a raiz
				Node node = createNode(indice);
				atualizarRaiz(bcontroleIndice, node);
//				é um short, acaba armazenando o valor errado
//				indice.setQtdIndice((byte) ((HeaderNode)node.getHeader()).getOrdemArvore());
				
				node.addTupla(chave);
				buffer.addBloco(indice, node);
			} else {
				Node folha = getRaiz(indice);
				boolean adicionou = false;

				while (!adicionou) {
					if (!folha.hasChild()) {
						// então é folha
						folha.addTupla(chave);
						folha.ordenar(buffer);

						if (folha.overflow()) {
							tratarOverflow(indice, folha);
						} else {
							folha.atualizar(buffer);
						}

						adicionou = true;
					} else {
						// não é folha ainda
						folha = (Node) buffer.getBloco(folha.getSubArvore(chave));
					}
				}

			}
		} else {
			log.Write(tupla.toString() + " imcompativel com o indice " + indice.getNome());
		}

	}

	private void tratarOverflow(IArquivo indice, Node folha) {
		Node node = createNode(indice);

		ArrayList<Chave> mchaves = folha.getMetadeChaves();

		Chave chaveCentral = mchaves.remove(0);

		for (Chave c : mchaves) {
			node.addTupla(c);
		}
		
		if(folha.hasChild()){
			ArrayList<RowId> mponteiros = folha.getMetadePonteiros();
			for (RowId ri : mponteiros) {
				node.addPonteiro(ri);
			}			
		}

		node.ordenar(buffer);
		
		if(!folha.hasChild()){
			node.setVizinho(folha.getVizinho());
			folha.setVizinho(node.getBlocoTupleId());			
		}
		
		buffer.addBloco(indice, node);
		node.atualizar(buffer);
		folha.atualizar(buffer);

		Node pai = getPai(folha, getRaiz(indice));

		if (pai == null) {
			pai = createNode(indice);
			pai.addPonteiro(folha.getBlocoTupleId());
			
			buffer.addBloco(indice, pai);
			atualizarRaiz(indice.getBlocoControle(), pai);			
		}

		pai.addTupla(chaveCentral);
		pai.addPonteiro(node.getBlocoTupleId());
		pai.ordenar(buffer);

		if (pai.overflow()) {
			if(pai.getBlocoId() == 52){
				System.out.println("overflow do 52");
			}
			tratarOverflow(indice, pai);
		}
		pai.atualizar(buffer);
	}

	private Node getPai(Node chaveDeBusca, Node raiz) {
		if(!raiz.hasChild()){
			return null;
		}
		if (raiz.hasPonteiro(chaveDeBusca)) {
			return raiz;
		}
		raiz = (Node) buffer.getBloco(raiz.getSubArvore(chaveDeBusca.menorChave()));
		return getPai(chaveDeBusca, raiz);
	}

	private Chave convertTuplaIntoChave(Descritor descritorIndice, ITupla tupla, Descritor descritorArquivo) {
		Coluna[] colunas = new Coluna[descritorIndice.getNumberOfColumns()];

		for (int i = 0; i < descritorIndice.getNumberOfColumns(); i++) {
			UnidadeDescricao descricao = descritorIndice.getUnidadeDescricao(i);

			int indexDescricaoNaTabela = -1;
			for (int j = 0; j < descritorArquivo.getNumberOfColumns(); j++) {
				if (descritorArquivo.getUnidadeDescricao(j).getNome().equals(descricao.getNome())) {
					indexDescricaoNaTabela = j;
					break;
				}
			}

			if (indexDescricaoNaTabela == -1)
				return null;

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

	public Node getRaiz(IArquivo indice) {
		return (Node) buffer.getBloco(
				new RowId(indice.getId(), ByteArrayTools.byteArrayToInt(indice.getBlocoControle().getIndices()), 0));
	}

	private Node createNode(IArquivo indice) {
		Node node = null;
		try {

			node = new Node(indice.getId(), indice.getBlocoControle().getProxBlocoLivre(), ETipoBlocoArquivo.indices,
					indice.getDescritor());
			
			node.setEvents(indice.getBlocoEvents());

		} catch (IncorrectFormatException e) {
			e.printStackTrace();
		}
		return node;
	}

	private void atualizarRaiz(BlocoControle bcontroleIndice, IBloco node) {
		bcontroleIndice.setIndices(ByteArrayTools.intToByteArray(node.getBlocoId()));
	}
	
	public ArrayList<ITupla> Buscar(String[] chavestring, byte indiceId){
		IArquivo indice = buffer.getGAFromCache().getArquivo(indiceId);
		Node node = (Node) getRaiz(indice);
		
		
		Chave chave = null;
		try {
			chave = new Chave(chavestring, null, indice.getDescritor(), null);
		} catch (IncorrectFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Buscar(chave, node);
	}

	public ArrayList<ITupla> Buscar(Chave chave, byte indiceId){
		return Buscar(chave, (Node) getRaiz(buffer.getGAFromCache().getArquivo(indiceId)));
	}
	
	private ArrayList<ITupla> Buscar(Chave chave, Node raiz){
		Node folha = raiz;
		if (folha == null)return null;
		ArrayList<ITupla> linhas = new ArrayList<ITupla>();
		
		while(folha.hasChild()){
			folha = (Node) buffer.getBloco(folha.getSubArvore(chave));			
		}
		
		while(folha != null && folha.hasChave(chave)){
			for(Chave key : folha.getChaves()){
				if(DadosNode.compareChave(key, chave) == 0){
					linhas.add(buffer.getBloco(key.getTarget())
							.getDados().getTupla(key.getTarget()));					
				}
			}
			RowId vizinho = folha.getVizinho();
			if(vizinho.getContainerId() != 0){
				folha = (Node) buffer.getBloco(vizinho);
			}else{
				folha = null;
			}
		}

		return linhas;
	}
}
