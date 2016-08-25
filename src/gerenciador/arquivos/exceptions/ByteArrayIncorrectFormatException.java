package gerenciador.arquivos.exceptions;

@SuppressWarnings("serial")
public class ByteArrayIncorrectFormatException extends Exception{
	
	public ByteArrayIncorrectFormatException(){
		
	}
	
	public ByteArrayIncorrectFormatException(String msg){
		super(msg);
	}
}
