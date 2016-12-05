package gerenciador;

import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

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
		return isContainerEquals(tuple.getContainerId());
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

	public byte[] getByteArray() throws IncorrectFormatException {
		// 8 bytes no total
		byte[] retorno = new byte[8];
		retorno[0] = this.containerId;
		
		byte[] blocoid = ByteArrayTools.intToByteArray(this.blocoId);
		retorno[1] = blocoid[1];
		retorno[2] = blocoid[2];
		retorno[3] = blocoid[3];
		
		ByteArrayTools.appendArrays(retorno, ByteArrayTools.intToByteArray(this.offSet), 4);
		
		return retorno;
	}

	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		this.containerId = dados[0];
		
		this.blocoId = ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 1, 3));
		
		this.offSet = ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 4, 4));		
	}
	
	@Override
	public boolean equals(Object q){
		RowId other = (RowId) q;
		
		if(isSameBloco(other) && offSet == other.offSet){
			return true;
		}
		
		return false;
	}
}
