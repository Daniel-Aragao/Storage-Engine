package gerenciador.TestesArquivo;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.enums.ETipoBloco;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class ArquivoTeste {

	private BlocoControle getBloco() throws IncorrectFormatException{
		String []props = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		return new BlocoControle(props, (byte)1);
	}
	
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
