package gerenciador.TestesArquivo.BlocoControle;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoColuna;
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
	
	@Test
	public void UnidadeDeveReceberCOD_AuthorI5EGerarByteArrayCorreto() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("COD_AUTHOR", ETipoColuna.inteiro, (byte)5);
		
		byte[] b = {0,0,0,0,0,0,0,0,0,0,00,0x43, 00,0x4F, 00,0x44, 00,0x5F, 00,0x41, 00,0x55, 
				00,0x54, 00,0x48, 00,0x4F, 00,0x52, GerenciadorArquivos.CARACTERE_INTEIRO,5}; 
		
		byte[] result = ud.getByteArray();
		
		Assert.assertArrayEquals(b, result);
	}
	
	@Test
	public void UnidadeDeveReceberNAME_AUTHORA100EGerarByteArrayCorreto() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("NAME_AUTHOR", ETipoColuna.string, (byte)100);
		
		byte[] b = {0,0,0,0,0,0,0,0,00,0x4E, 00,0x41, 00,0x4D, 00,0x45, 00,0x5F, 00,0x41, 00,0x55,
				00, 0x54, 00,0x48, 00,0x4F, 00,0x52, GerenciadorArquivos.CARACTERE_STRING,100}; 
		
		byte[] result = ud.getByteArray();
		
		Assert.assertArrayEquals(b, result);
	}
	
	@Test
	public void DescritorDeveReceberCOD_AuthorI5ENAME_AUTHORA100EGerarByteArrayCorreto() 
			throws IncorrectFormatException{
		String[] props = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		Descritor d = new Descritor(props);
		
		byte[] a = {0,0,0,0,0,0,0,0,0,0,00,0x43, 00,0x4F, 00,0x44, 00,0x5F, 00,0x41, 00,0x55,
				00,0x54, 00,0x48, 00,0x4F, 00,0x52, GerenciadorArquivos.CARACTERE_INTEIRO,5,
				0,0,0,0,0,0,0,0,00,0x4E, 00,0x41, 00,0x4D, 00,0x45, 00,0x5F, 00,0x41, 00,0x55, 00,
				0x54, 00,0x48, 00,0x4F, 00,0x52, GerenciadorArquivos.CARACTERE_STRING,100}; 
		
		byte[] result = d.getByteArray();
		
		Assert.assertArrayEquals(a, result);
	}
	
	@Test
	public void DescritorDeveReceberCOD_AuthorI5ENAME_AUTHORA100ERetornarSize64() throws IncorrectFormatException{
		String[] props = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		Descritor d = new Descritor(props);
		
		Assert.assertEquals(UnidadeDescricao.UNIDADE_DESCRICAO_SIZE*2, d.getDescritorSize());
	}
	
	@Test
	public void DescritorDeveReceberCOD_AuthorI5ENAME_AUTHORA100EGerarByteArrayDoName_AuthorAposGetUnidade() 
			throws IncorrectFormatException{
		String[] props = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		Descritor d = new Descritor(props);
		
		byte[] b = {0,0,0,0,0,0,0,0,00,0x4E, 00,0x41, 00,0x4D, 00,0x45, 00,0x5F, 00,0x41, 00,0x55,
				00, 0x54, 00,0x48, 00,0x4F, 00,0x52, GerenciadorArquivos.CARACTERE_STRING,100}; 
		
		byte[] result = d.getUnidadeDescricao(1).getByteArray();
		
		Assert.assertArrayEquals(b, result);
	}
	
	@Test
	public void DescritorDeveByteArrayEGerarCOD_AuthorI5ENAME_AUTHORA100frombytearray() 
			throws IncorrectFormatException{
		byte[] b = {0,0,0,0,0,0,0,0,0,0,00,0x43, 00,0x4F, 00,0x44, 00,0x5F, 00,0x41, 00,0x55,
				00,0x54, 00,0x48, 00,0x4F, 00,0x52, GerenciadorArquivos.CARACTERE_INTEIRO,5,
				0,0,0,0,0,0,0,0,00,0x4E, 00,0x41, 00,0x4D, 00,0x45, 00,0x5F, 00,0x41, 00,0x55, 00,
				0x54, 00,0x48, 00,0x4F, 00,0x52, GerenciadorArquivos.CARACTERE_STRING,100};
		Descritor d = new Descritor(b);
		
		//String[] props = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		
		Assert.assertEquals("COD_AUTHOR", d.getUnidadeDescricao(0).getNome());
		Assert.assertEquals("NAME_AUTHOR", d.getUnidadeDescricao(1).getNome());
		
		Assert.assertEquals(5, d.getUnidadeDescricao(0).getTamanho());
		Assert.assertEquals(100, d.getUnidadeDescricao(1).getTamanho());
		
		Assert.assertEquals(ETipoColuna.inteiro, d.getUnidadeDescricao(0).getTipo());
		Assert.assertEquals(ETipoColuna.string, d.getUnidadeDescricao(1).getTipo());
	}
	
}
