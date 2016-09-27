package gerenciador.arquivos.blocos;

import gerenciador.arquivos.enums.ETipoBloco;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class HeaderBloco implements IBinarizable<HeaderBloco>{

	private byte containerId;
	private int blocoId; // 3 bytes
	private ETipoBloco tipo; // bloco de dados ou bloco de índices
	private int bytesUsados; // 3 bytes
	
	public HeaderBloco(byte containerId, int BlockId, ETipoBloco tipoBloco) {
		this.blocoId = BlockId;
		this.containerId = containerId;
		this.tipo = tipoBloco;
		bytesUsados = 8;
	}
	
	public HeaderBloco(byte[] dados) {
		fromByteArray(dados);
	}
	
	public boolean isFull(int size){
		return this.bytesUsados + size > 4096;
	}
	
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

	public int getBytesUsados() {
		return bytesUsados;
	}

	public void setBytesUsados(int bytesUsados) {
		this.bytesUsados = bytesUsados;
	}
	
	public void incBytes(int bytes){
		this.bytesUsados += bytes;
	}
	
	public void decBytes(int bytes){
		this.bytesUsados -= bytes;
	}

	public byte getContainerId() {
		return containerId;
	}

	public int getBlocoId() {
		return blocoId;
	}

	public ETipoBloco getTipo() {
		return tipo;
	}

	@Override
	public void fromByteArray(byte[] dados) {
		this.containerId = dados[0];
		
		this.blocoId = ByteArrayTools
				.byteArrayToInt(ByteArrayTools.subArray(dados, 1, 3));
		
		this.tipo = ETipoBloco.getByValue(dados[4]);
		
		this.bytesUsados = ByteArrayTools
				.byteArrayToInt(ByteArrayTools.subArray(dados, 5, 3));		
	}

}
