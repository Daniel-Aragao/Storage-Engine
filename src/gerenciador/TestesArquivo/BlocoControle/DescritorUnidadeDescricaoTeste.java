package gerenciador.TestesArquivo.BlocoControle;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.arquivos.ETipoColuna;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.exceptions.IncorrectFormatException;

public class DescritorUnidadeDescricaoTeste {

	@Test
	public void DeveReceberAPropriedadeNomeAutorEMapeala() throws IncorrectFormatException{
		String[] propriedades = {"NAME_AUTHOR[A(100)]"};
		
		Descritor d = new Descritor(propriedades);
		
		UnidadeDescricao ud = d.getUnidadeDescricao(0);		
		
		Assert.assertEquals("NAME_AUTHOR", ud.getNome());
		Assert.assertEquals(ETipoColuna.string, ud.getTipo());
		Assert.assertEquals(100, ud.getTamanho());
	}
	
	@Test
	public void DeveReceberAPropriedadeCOD_AUTHORI5EMapeala() throws IncorrectFormatException{
		String[] propriedades = {"COD_AUTHOR[I(5)]"};
		
		Descritor d = new Descritor(propriedades);
		
		UnidadeDescricao ud = d.getUnidadeDescricao(0);		
		
		Assert.assertEquals("COD_AUTHOR", ud.getNome());
		Assert.assertEquals(ETipoColuna.inteiro, ud.getTipo());
		Assert.assertEquals(5, ud.getTamanho());
	}
	
	@Test
	public void DeveReceberDuasPropriedadesERetornarOtamanhoCorreto() throws IncorrectFormatException{
		String[] propriedades = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		
		Descritor d = new Descritor(propriedades);
		
		Assert.assertEquals(UnidadeDescricao.UNIDADE_DESCRICAO_SIZE*2, d.getDescritorSize());		
		
	}
	
	@Test
	public void DeveReceberDuasPropriedadesERetornarOLengthDoArrayCorreto() throws IncorrectFormatException{
		String[] propriedades = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		
		Descritor d = new Descritor(propriedades);
		
		Assert.assertEquals(UnidadeDescricao.UNIDADE_DESCRICAO_SIZE*2, d.getByteArray().length);		
	}
	
	@Test(expected = IncorrectFormatException.class)
	public void DeveReceberUmNomeMuitoGrandeEGerarIncorrectFormatException() throws IncorrectFormatException{
		String[] propriedades = {"COD_AUTHOR[I(5)]","NAME_AUTHOR12345[A(100)]"};
				
		Descritor d = new Descritor(propriedades);
	}
	
	@Test
	public void DeveReceberUmNomeIgualAoLimiteENaoGerarIncorrectFormatException() throws IncorrectFormatException{
		String[] propriedades = {"COD_AUTHOR[I(5)]","NAME_AUTHOR1234[A(100)]"};
				
		Descritor d = new Descritor(propriedades);
		
		Assert.assertEquals("NAME_AUTHOR1234", d.getUnidadeDescricao(1).getNome());
	}
	
	@Test
	public void DeveReceberName_AuthorA100EGerarByteArrayComUltimoByte100() throws IncorrectFormatException{
		
		UnidadeDescricao ud = new UnidadeDescricao("NAME_AUTHOR", ETipoColuna.string, (byte)100);
		
		byte tam = ud.getByteArray()[UnidadeDescricao.NOME_SIZE+1];
		
		Assert.assertEquals(100, tam);
	}
	@Test
	public void DeveReceberName_AuthorA100EGerarByteArrayComPenultimoUltimoByteA() throws IncorrectFormatException{
		
		UnidadeDescricao ud = new UnidadeDescricao("NAME_AUTHOR", ETipoColuna.string, (byte)100);
		
		byte tam = ud.getByteArray()[UnidadeDescricao.NOME_SIZE];
		
		Assert.assertEquals('A', tam);
	}
	
	@Test
	public void DeveReceberCOD_AuthorI5EGerarByteArrayComPenultimoUltimoByteI() throws IncorrectFormatException{
		
		UnidadeDescricao ud = new UnidadeDescricao("COD_AUTHOR", ETipoColuna.inteiro, (byte)5);
		
		byte tam = ud.getByteArray()[UnidadeDescricao.NOME_SIZE];
		
		Assert.assertEquals('I', tam);
	}
	
	
}
