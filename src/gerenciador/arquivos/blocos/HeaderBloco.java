package gerenciador.arquivos.blocos;

import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.utils.ByteArrayTools;

public class HeaderBloco implements IHeader{

	private byte containerId;
	private int blocoId; // 3 bytes
	private ETipoBlocoArquivo tipo; // bloco de dados ou bloco de índices
	private int bytesUsados; // 3 bytes
	
	public HeaderBloco(byte containerId, int BlockId, ETipoBlocoArquivo tipoBloco) {
		this.blocoId = BlockId;
		this.containerId = containerId;
		this.tipo = tipoBloco;
		bytesUsados = 8;
	}
	
	public HeaderBloco(byte[] dados) {
		fromByteArray(dados);
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#isFull(int)
	 */
	@Override
	public boolean isFull(int size){
		return this.bytesUsados + size > 4096;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#getByteArray()
	 */
	@Override
	public byte[] getByteArray() {
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

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#getBytesUsados()
	 */
	@Override
	public int getBytesUsados() {
		return bytesUsados;
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#setBytesUsados(int)
	 */
	@Override
	public void setBytesUsados(int bytesUsados) {
		this.bytesUsados = bytesUsados;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#incBytes(int)
	 */
	@Override
	public void incBytes(int bytes){
		this.bytesUsados += bytes;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#decBytes(int)
	 */
	@Override
	public void decBytes(int bytes){
		this.bytesUsados -= bytes;
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#getContainerId()
	 */
	@Override
	public byte getContainerId() {
		return containerId;
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#getBlocoId()
	 */
	@Override
	public int getBlocoId() {
		return blocoId;
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#getTipo()
	 */
	@Override
	public ETipoBlocoArquivo getTipo() {
		return tipo;
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IHeader#fromByteArray(byte[])
	 */
	@Override
	public void fromByteArray(byte[] dados) {
		this.containerId = dados[0];
		
		this.blocoId = ByteArrayTools
				.byteArrayToInt(ByteArrayTools.subArray(dados, 1, 3));
		
		this.tipo = ETipoBlocoArquivo.getByValue(dados[4]);
		
		this.bytesUsados = ByteArrayTools
				.byteArrayToInt(ByteArrayTools.subArray(dados, 5, 3));		
	}

}
