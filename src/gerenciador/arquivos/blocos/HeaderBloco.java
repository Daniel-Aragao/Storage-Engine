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
		bytesUsados = 0;
	}
	
	public HeaderBloco(byte[] dados) {
		fromByteArray(dados);
	}
	
	@Override
	public byte[] getByteArray() {
		throw new RuntimeException("Não implementado");
	}

	public int getBytesUsados() {
		return bytesUsados;
	}

	public void setBytesUsados(int bytesUsados) {
		this.bytesUsados = bytesUsados;
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
