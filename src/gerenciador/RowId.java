package gerenciador;

import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;

public class RowId implements IBinarizable<RowId>{
	public static final int ROWID_SIZE = 8;
	private byte containerId; // 1 byte
	private int blocoId; // 3 bytes
	private int offSet; // 4 bytes

	public RowId(byte cId, int bId, int off) {
		this.containerId = cId;
		this.blocoId = bId;
		this.offSet = off;
	}
	
	public RowId(byte[] dados) throws IncorrectFormatException{
		fromByteArray(dados);
	}

	public byte getContainerId() {
		return containerId;
	}

	public int getBlocoId() {
		return blocoId;
	}

	public int getOffSet() {
		return offSet;
	}

	public boolean isBlocoEquals(int tuple) {
		return tuple == this.blocoId;
	}

	public boolean isBlocoEquals(RowId tuple) {
		return isBlocoEquals(tuple.getBlocoId());
	}

	public boolean isContainerEquals(RowId tuple) {
		return isBlocoEquals(tuple.getBlocoId());
	}

	public boolean isContainerEquals(int contId) {
		return this.containerId == contId;
	}

	public boolean isSameBloco(RowId tuple) {
		return isContainerEquals(tuple) && isBlocoEquals(tuple);
	}

	@Override
	public String toString() {
		return containerId + "." + blocoId + "." + offSet;
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		// 8 bytes no total
		throw new RuntimeException("Não implementado");
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");
		
	}
}
