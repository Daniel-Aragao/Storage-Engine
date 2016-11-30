package gerenciador.indice.blocos;

import gerenciador.arquivos.blocos.IHeader;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class HeaderNode implements IHeader{
	
	private byte containerId;
	private int blocoId; // 3 bytes
	private ETipoBlocoArquivo tipo; // bloco de dados ou bloco de índices
	private int bytesUsados; // 3 bytes

	public HeaderNode(byte containerId, int blockId, ETipoBlocoArquivo tipoBloco) {
		this.blocoId = blockId;
		this.containerId = containerId;
		this.tipo = tipoBloco;
		bytesUsados = 8;
	}
	
	public HeaderNode(byte[] dados) throws IncorrectFormatException{
		fromByteArray(dados);
	}

	public byte getContainerId() {
		return containerId;
	}

	public int getBytesUsados() {
		return bytesUsados;
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] retorno = new byte[8];
		
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
		
	}

	@Override
	public boolean isFull(int size) {
		return this.bytesUsados + size > 4096;
	}

	@Override
	public void setBytesUsados(int bytesUsados) {
		this.bytesUsados = bytesUsados;
		
	}

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

}
