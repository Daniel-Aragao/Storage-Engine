package gerenciador.indice.blocos;

import java.util.ArrayList;

import gerenciador.GerenciadorBuffer;
import gerenciador.RowId;
import gerenciador.arquivos.blocos.ColunaInt;
import gerenciador.arquivos.blocos.DadosBloco;
import gerenciador.arquivos.blocos.HeaderBloco;
import gerenciador.arquivos.blocos.IDados;
import gerenciador.arquivos.blocos.IHeader;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.enums.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.IBlocoEvents;
import gerenciador.arquivos.interfaces.ITupla;
import gerenciador.utils.ByteArrayTools;

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
		dados = new DadosNode(descritor, header);
	}

	public Node(byte[] dados, Descritor descritor) throws IncorrectFormatException {
		this.descritor = descritor;
		this.fromByteArray(dados);
	}
	
	private short calcularOrdem(Descritor descritor){
		short tamanho_chaves = RowId.ROWID_SIZE; // chave inicia-se com um ponteiro
		short tamanho_ponteiros = RowId.ROWID_SIZE; // ponteiro para Nodes
		
		for(UnidadeDescricao ud : descritor.getDescritores()){
			if(ud.getTipo() == ETipoColuna.string){
				tamanho_chaves += ud.getTamanho()*2; // string ocupad 2 bytes
			}else if(ud.getTipo() == ETipoColuna.inteiro){
				tamanho_chaves += 4; // tamanho de um inteiro
			}
			tamanho_chaves += 2; // tamanho da propriedade "tamanho da coluna"
		}
		tamanho_chaves += 4; // tamanho da propriedade "tamanho da tupla"
		tamanho_chaves += RowId.ROWID_SIZE; // rowid para qual a chave aponta

		short ordem = (short) ((BlocoControle.TAMANHO_BLOCO 
				- Node.HEADER_BLOCO_INDICE_SIZE - tamanho_ponteiros) // ponteiro inicial
				/(tamanho_chaves + tamanho_ponteiros) + 1);// + 1 é o ponteiro já descontado do total
		
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
		return dados.getSizePonteiros() > header.getMaxPonteiros() || 
				dados.getSizeChaves() > header.getMaxChaves();
	}
	
	public boolean hasChild(){
		return this.dados.getSizePonteiros() != 0;
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
		dados.addTupla(tupla);
		
		//NÃO salvar em disco, operação manual após a ordenação externa e overflow
	}
	public void ordenar(GerenciadorBuffer buffer) {
		dados.ordenarChaves();
		if(this.hasChild()){
			dados.ordenarPonteiros(buffer);	
		}
	}

	@Override
	public RowId getNextTupleId() {
		RowId tupleId = new RowId(this.header.getContainerId(), getBlocoId(), this.header.getBytesUsados());

		return tupleId;
	}

	@Override
	public void addTupla(byte[] tuplaBytes) throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");

	}

	@Override
	public void removeTupla(ITupla tupla) {
		throw new RuntimeException("Não implementado");

	}

	@Override
	public void removeTupla(int index) {
		throw new RuntimeException("Não implementado");

	}

	@Override
	public RowId getBlocoTupleId() {
		return new RowId(this.header.getContainerId(), this.getBlocoId(), -1);
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] retorno = new byte[BlocoControle.TAMANHO_BLOCO];

		byte[] content = ByteArrayTools.concatArrays(header.getByteArray(), dados.getByteArray());

		ByteArrayTools.appendArrays(retorno, content, 0);

		return retorno;
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		this.header = new HeaderNode(ByteArrayTools.subArray(dados, 0, HEADER_BLOCO_INDICE_SIZE));

		this.dados = new DadosNode(ByteArrayTools.subArray(dados, HEADER_BLOCO_INDICE_SIZE, dados.length - HEADER_BLOCO_INDICE_SIZE),
				this.getBlocoTupleId(), descritor, header);
	}

	public RowId getSubArvore(Chave tupla) {
		if(hasChild()){
			return dados.getSubArvore(tupla);			
		}
		return null;
	}
	
	public void atualizar(GerenciadorBuffer buffer){
		events.blocoAlterado(this);
		buffer.updateBlocoNoBuffer(this);
	}
	
	public Chave menorChave(){
		return this.dados.getChave(0);
	}
	
	public ArrayList<Chave> getMetadeChaves(){
		return dados.getMetadeChaves();
	}
	
	public ArrayList<RowId> getMetadePonteiros(){
		return dados.getMetadePonteiros();
	}

	public void addPonteiro(RowId ri) {
		dados.addPonteiro(ri);
	}
	
	public boolean hasPonteiro(Node noh){
		return dados.hasPonteiro(noh);
	}

	public boolean hasChave(Chave chave) {
		return dados.hasChave(chave);
		
	}

	public void setVizinho(RowId blocoTupleId) {
		this.header.setVizinho(blocoTupleId);
		
	}

	public RowId getVizinho() {
		return header.getVizinho();
	}

	public ArrayList<Chave> getChaves() {
		return dados.getChaves();
	}
	
	@Override
	public String toString() {
		String retorno = "";
		byte cId = header.getContainerId();
		int bId = header.getBlocoId();
		int i = 0;
		for (i = 0; i < dados.size(); i++) {
			Chave tupla = (Chave) dados.getTupla(i);
			try{
				retorno += dados.getPonteiro(i).toString()+"\n";				
			}catch(IndexOutOfBoundsException e){
				
			}
			retorno += "(" + tupla.getTupleId().toString() + ") "
			// retorno += "("+cId + " " + bId + " " + dados.getOffSet(i) + ") "
					+ tupla.toString() + "(" + tupla.getTarget().toString() + ") " + "\n";
		}
		try{
			retorno += dados.getPonteiro(i).toString()+"\n";				
		}catch(IndexOutOfBoundsException e){
			
		}

		return retorno + "\n";
	}
}
