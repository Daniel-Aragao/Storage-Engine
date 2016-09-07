package gerenciador.arquivos.exceptions;

@SuppressWarnings("serial")
public class IncorrectFormatException extends Exception{
	
	public IncorrectFormatException(){
		
	}
	
	public IncorrectFormatException(String msg){
		super(msg);
	}
}
