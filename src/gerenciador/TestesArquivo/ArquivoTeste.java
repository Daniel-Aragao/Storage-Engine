package gerenciador.TestesArquivo;

import org.junit.Test;

import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;

public class ArquivoTeste {

	@SuppressWarnings("unused")
	private BlocoControle getBloco() throws IncorrectFormatException{
		String []props = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		return new BlocoControle("Author", props, (byte)1);
	}
	
	@SuppressWarnings("unused")
	private Descritor getDescritor() throws IncorrectFormatException{
		String descs[] = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		return new Descritor(descs); 
	}
	
	@Test
	public void deveReceberBlocoControleEGetByteArrayDeveVirCorreto() throws IncorrectFormatException{
		
		
	}
	
	@Test
	public void deveReceberBlocoControleEAdicionarLinhaEGetByteArrayDeveVirCorreto() throws IncorrectFormatException{
		
		
	}
	
	
}
