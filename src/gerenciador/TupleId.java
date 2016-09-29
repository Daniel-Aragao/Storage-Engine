package gerenciador;

public class TupleId {
	private int containerId;
	private int blocoId;
	private int offSet;
	
	public TupleId(int cId, int bId, int off){
		this.containerId = cId;
		this.blocoId = bId;
		this.offSet = off;
	}

	public int getContainerId(){
		return containerId;
	}
	public int getBlocoId(){
		return blocoId;
	}
	public int getOffSet(){
		return offSet;
	}
	
	public boolean isBlocoEquals(int tuple){
		return tuple == this.blocoId;
	}
	public boolean isBlocoEquals(TupleId tuple){
		return isBlocoEquals(tuple.getBlocoId());
	}
	
	public boolean isContainerEquals(TupleId tuple){
		return isBlocoEquals(tuple.getBlocoId());
	}
	public boolean isContainerEquals(int contId){
		return this.containerId == contId;
	}
	
	public boolean isSameBloco(TupleId tuple){
		return isContainerEquals(tuple) && isBlocoEquals(tuple);
	}
}
