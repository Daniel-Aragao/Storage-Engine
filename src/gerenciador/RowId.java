package gerenciador;

public class RowId {
	private byte containerId;
	private int blocoId;
	private int offSet;

	public RowId(byte cId, int bId, int off) {
		this.containerId = cId;
		this.blocoId = bId;
		this.offSet = off;
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
}
