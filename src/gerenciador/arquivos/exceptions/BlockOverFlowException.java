package gerenciador.arquivos.exceptions;

@SuppressWarnings("serial")
public class BlockOverFlowException extends Exception{
	
	private int blocoId;
	
	public BlockOverFlowException(){
		
	}
	
	public BlockOverFlowException(String msg, int blocoId){
		super(msg);
		this.blocoId = blocoId;
	}

	public int getBlocoId() {
		return blocoId;
	}
}
