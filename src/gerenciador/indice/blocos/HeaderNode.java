package gerenciador.indice.blocos;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.IHeader;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class HeaderNode implements IHeader{
	public static final int TAMANHO_HEADER = 20;
	
	private byte containerId;
	private int blocoId; // 3 bytes
	private ETipoBlocoArquivo tipo; // bloco de dados ou bloco de índices
	private int bytesUsados; // 3 bytes
	
	private short ordemArvore; // 2 bytes
	private short qtdPonteiros;// 2 bytes
	private RowId vizinho; // 8 bytes
	
	private int minPonteiros; // p min
	private int maxPonteiros;
	private int minChaves; // k min
	private int maxChaves;

	public HeaderNode(byte containerId, int blockId, ETipoBlocoArquivo tipoBloco, short ordemArvore) {
		this.blocoId = blockId;
		this.containerId = containerId;
		this.tipo = tipoBloco;
		bytesUsados = TAMANHO_HEADER;
		this.ordemArvore = ordemArvore;
		vizinho = new RowId((byte) 0, 0, 0);
		kpConfig();
	}
	
	private void kpConfig(){
		this.minPonteiros = ordemArvore / 2 + ((ordemArvore % 2 == 0) ? 0 : 1); 
		this.maxPonteiros = ordemArvore;
		this.minChaves = (ordemArvore - 1)/2;
		this.maxChaves = ordemArvore - 1;
	}
	
	public void incQtdPonteiros(int i){
		qtdPonteiros += i;
	}
	
	public void decQtdPonteiros(int i){
		qtdPonteiros -= i;
	}
	
	public short getQtdPonteiros(){
		return this.qtdPonteiros;
	}
	
	public HeaderNode(byte[] dados) throws IncorrectFormatException{
		fromByteArray(dados);		
		kpConfig();		
	}

	public byte getContainerId() {
		return containerId;
	}

	public int getBytesUsados() {
		return bytesUsados;
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] retorno = new byte[TAMANHO_HEADER];
		
		retorno[0] = containerId;
		
		byte[] blocoid = ByteArrayTools.intToByteArray(this.blocoId);
		retorno[1] = blocoid[1];
		retorno[2] = blocoid[2];
		retorno[3] = blocoid[3];
		
		retorno[4] = this.tipo.getValor();
		
		byte[] bytesUsados = ByteArrayTools.intToByteArray(this.bytesUsados);
		retorno[5] = bytesUsados[1];
		retorno[6] = bytesUsados[2];
		retorno[7] = bytesUsados[3];
		
		byte[] ordem = ByteArrayTools.intToByteArray(this.ordemArvore);
		retorno[8] = ordem[2];
		retorno[9] = ordem[3];
		
		byte[] qtdPonteiros = ByteArrayTools.intToByteArray(this.qtdPonteiros);
		retorno[10] = qtdPonteiros[2];
		retorno[11] = qtdPonteiros[3];
		
		ByteArrayTools.appendArrays(retorno, vizinho.getByteArray(), 12);
		
		return retorno;
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		this.containerId = dados[0];
		
		this.blocoId = ByteArrayTools
				.byteArrayToInt(ByteArrayTools.subArray(dados, 1, 3));
		
		this.tipo = ETipoBlocoArquivo.getByValue(dados[4]);
		
		this.bytesUsados = ByteArrayTools
				.byteArrayToInt(ByteArrayTools.subArray(dados, 5, 3));
		
		this.ordemArvore = (short) ByteArrayTools
				.byteArrayToInt(ByteArrayTools.subArray(dados, 8, 2));
		
		this.qtdPonteiros = (short) ByteArrayTools
				.byteArrayToInt(ByteArrayTools.subArray(dados, 10, 2));
		
		this.vizinho = new RowId(ByteArrayTools
				.subArray(dados, 12, RowId.ROWID_SIZE));
	}

	@Override
	public boolean isFull(int size) {
		return this.bytesUsados + size > BlocoControle.TAMANHO_BLOCO;
	}

//	@Override
//	public void setBytesUsados(int bytesUsados) {
//		this.bytesUsados = bytesUsados;
//	}

	@Override
	public void incBytes(int bytes) {
		this.bytesUsados += bytes;
		
	}

	@Override
	public void decBytes(int bytes) {
		this.bytesUsados -= bytes;
		
	}

	@Override
	public int getBlocoId() {
		return blocoId;
	}

	@Override
	public ETipoBlocoArquivo getTipo() {
		return tipo;
	}

	public int getMinPonteiros() {
		return minPonteiros;
	}

	public int getMaxPonteiros() {
		return maxPonteiros;
	}

	public int getMinChaves() {
		return minChaves;
	}

	public int getMaxChaves() {
		return maxChaves;
	}

	public short getOrdemArvore() {
		return ordemArvore;
	}
	
	public void setVizinho(RowId blocoTupleId) {
		vizinho = blocoTupleId;
	}

	public RowId getVizinho() {
		return vizinho;
	}

}
