package gerenciador.arquivos.blocos.components;

import gerenciador.arquivos.interfaces.IBinarizable;

public class HeaderBloco implements IBinarizable<HeaderBloco>{

	private byte containerId;
	private int blocoId; // 3 bytes
	private byte tipo;
	private int bytesUsados; // 3 bytes
	
	public HeaderBloco() {
		// TODO Auto-generated constructor stub
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

	public byte getTipo() {
		return tipo;
	}

}
