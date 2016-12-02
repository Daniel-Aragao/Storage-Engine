package gerenciador.indice.blocos;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.IDados;
import gerenciador.arquivos.blocos.IHeader;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.IBlocoEvents;
import gerenciador.arquivos.interfaces.ITupla;

public class Node implements IBloco {
	public static final int HEADER_BLOCO_INDICE_SIZE = HeaderNode.TAMANHO_HEADER;

	private Descritor descritor;
	private IBlocoEvents events;
	private DadosNode dados;
	private HeaderNode header;

	public Node(byte containerId, int BlockId, ETipoBlocoArquivo tipoBloco, Descritor descritor)
			throws IncorrectFormatException {
		this.descritor = descritor;
		if (tipoBloco != ETipoBlocoArquivo.indices) {
			throw new IncorrectFormatException("Tipo de bloco deve ser de indice");
		}
		
		short ordemArvore = calcularOrdem(descritor);	
		
		header = new HeaderNode(containerId, BlockId, tipoBloco, ordemArvore);
		dados = new DadosNode(descritor);
	}

	public Node(byte[] dados, Descritor descritor) throws IncorrectFormatException {
		this.descritor = descritor;
		this.fromByteArray(dados);
	}
	
	private short calcularOrdem(Descritor descritor){
		short tamanho_chaves = 0;
		short tamanho_ponteiros = RowId.ROWID_SIZE;
		
		for(UnidadeDescricao ud : descritor.getDescritores()){
			tamanho_chaves += ud.getTamanho();
		}

		short ordem = (short) ((BlocoControle.TAMANHO_BLOCO - Node.HEADER_BLOCO_INDICE_SIZE - tamanho_ponteiros)
				/(tamanho_chaves + tamanho_ponteiros) + 1);// + 1 � o ponteiro j� retirado do total
		
		return ordem;
	}

	@Override
	public void setEvents(IBlocoEvents events) {
		this.events = events;

	}
	
	public boolean isFull(){
		return dados.getSizePonteiros() == header.getMaxChaves();
	}
	
	public boolean overflow(){
		return dados.getSizePonteiros() > header.getMaxChaves();
	}
	
	public boolean hasChild(){
		return this.dados.getSizeChaves() != 0;
	}

	@Override
	public IHeader getHeader() {
		return header;
	}

	@Override
	public int getBlocoId() {
		return header.getBlocoId();
	}

	@Override
	public IDados getDados() {
		return this.dados;
	}

	@Override
	public void addTupla(ITupla tupla) {		
		/*
		 * adicionar tupla e possibilitar alguma maneira de ordenar os ponteiros da �rvore
		 */
		if(this.hasChild()){
			throw new RuntimeException("N�o implementado");
		}else{
			dados.ordenarFolha((Chave) tupla);
		}
		
		// se sofrer um overflow N�O salvar em disco
		
		if(!overflow()){
			events.blocoAlterado(this);
		}
		
		throw new RuntimeException("N�o implementado");

	}

	@Override
	public RowId getNextTupleId() {
		RowId tupleId = new RowId(this.header.getContainerId(), getBlocoId(), this.header.getBytesUsados());

		return tupleId;
	}

	@Override
	public void addTupla(byte[] tuplaBytes) throws IncorrectFormatException {
		throw new RuntimeException("N�o implementado");

	}

	@Override
	public void removeTupla(ITupla tupla) {
		throw new RuntimeException("N�o implementado");

	}

	@Override
	public void removeTupla(int index) {
		throw new RuntimeException("N�o implementado");

	}

	@Override
	public RowId getBlocoTupleId() {
		return new RowId(this.header.getContainerId(), this.getBlocoId(), -1);
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		// intercalar cada chave com um rowid
		throw new RuntimeException("N�o implementado");
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		// intercalados chaves com rowid
		throw new RuntimeException("N�o implementado");

	}

	public RowId getSubArvore(Chave tupla) {
		return dados.getSubArvore(tupla);
	}

	public void addWithOverflow(Chave chave) {
		throw new RuntimeException("N�o implementado");
		
	}
	
	public void atualizar(){
		events.blocoAlterado(this);
	}
}
