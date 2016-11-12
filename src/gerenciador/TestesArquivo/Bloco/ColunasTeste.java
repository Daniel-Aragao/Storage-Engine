package gerenciador.TestesArquivo.Bloco;

import org.junit.Test;
import org.junit.Assert;

import gerenciador.arquivos.blocos.ColunaInt;
import gerenciador.arquivos.blocos.ColunaString;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;

public class ColunasTeste {
	/*
	101|LUIZ RUFFATO|
	20|JORGE AMADO|
	13|CHARLES DICKENS|
	 **/
	@Test
	public void ColunaIntRecebe101EDeveFormarSeusDadosCorretamente() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.inteiro, (byte) 5);
		ColunaInt col = new ColunaInt("101", ud);
		
		Assert.assertEquals(6,col.getColumnSize());
		Assert.assertEquals(101,col.getDado().intValue());
		
	}
	@Test
	public void ColunaIntRecebe5768EDeveFormarSeusDadosCorretamente() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.inteiro, (byte) 5);
		ColunaInt col = new ColunaInt("5768", ud);
		
		Assert.assertEquals(6,col.getColumnSize());
		Assert.assertEquals(5768,col.getDado().intValue());
		
	}
	@Test(expected=IncorrectFormatException.class)
	public void ColunaIntRecebe57A68EDeveLancarException() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.inteiro, (byte) 5);
		@SuppressWarnings("unused")
		ColunaInt col = new ColunaInt("57A68", ud);
	}
	
	@Test
	public void ColunaStringRecebeLUIZ_RUFFATOEDeveFormarDadosCorretamente() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.string, (byte) 30);
		ColunaString col = new ColunaString("LUIZ RUFFATO", ud);
		
		Assert.assertEquals(2+(12*2),col.getColumnSize());
		Assert.assertEquals("LUIZ RUFFATO",col.getDado());		
	}
	
	@Test
	public void ColunaStringRecebeJORGE_AMADOEDeveFormarDadosCorretamente() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.string, (byte) 30);
		ColunaString col = new ColunaString("JORGE AMADO", ud);
		
		Assert.assertEquals(2+(11*2),col.getColumnSize());
		Assert.assertEquals("JORGE AMADO",col.getDado());		
	}
	
	@Test(expected=IncorrectFormatException.class)
	public void ColunaStringRecebe0123456789012345678901234567890EDeveRetornarErro() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.string, (byte) 30);
		@SuppressWarnings("unused")
		ColunaString col = new ColunaString("0123456789012345678901234567890", ud);	
	}
	
	@Test
	public void ColunaIntRecebe5768EmByteArrayEDeveFormarSeusDadosCorretamente() throws IncorrectFormatException{
		byte[] construcao = {0,6, 				// tamanho da coluna
							0,0,22,(byte) 136};	// dados da coluna
		ColunaInt col = new ColunaInt(construcao);
		
		Assert.assertEquals(6,col.getColumnSize());
		Assert.assertEquals(5768,col.getDado().intValue());		
	}
	
	@Test
	public void ColunaStringRecebeJORGE_AMADOEmByteArrayEDeveFormarSeusDadosCorretamente() throws IncorrectFormatException{
		byte[] construcao = {0,24, 			// tamanho da coluna
				00,0x4A, 00,0x4F, 00,0x52, 
				00,0x47, 00,0x45, 00,0x5F, 
				00,0x41, 00,0x4D, 00,0x41, 
				00,0x44, 00,0x4F};			// dados da coluna
		
		ColunaString col = new ColunaString(construcao);
		
		Assert.assertEquals(24, col.getColumnSize());
		Assert.assertEquals("JORGE_AMADO",col.getDado());		
	}
	
	@Test
	public void ColunaStringRecebeLUIZ_RUFFATOEmByteArrayEDeveFormarSeusDadosCorretamente() throws IncorrectFormatException{
		byte[] construcao = {0,26, 						// tamanho da coluna
				00,0x4C, 00,0x55, 00,0x49,
				00,0x5A, 00,0x5F, 00,0x52, 
				00,0x55, 00,0x46, 00,0x46, 
				00,0x41, 00,0x54, 00,0x4F};	// dados da coluna
		
		ColunaString col = new ColunaString(construcao);
		
		Assert.assertEquals(26, col.getColumnSize());
		Assert.assertEquals("LUIZ_RUFFATO",col.getDado());	
	}
	
	@Test
	public void ColunaIntRecebe5768EDeveFormarSeusDadosCorretamenteByteArray() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.inteiro, (byte) 5);
		ColunaInt col = new ColunaInt("5768", ud);
		
		byte[] b = {0,6, 				// tamanho da coluna
					0,0,22,(byte) 136};	// dados da coluna
		
		byte[] resultado = col.getByteArray();
		
		Assert.assertArrayEquals(b,resultado);		
	}
	
	@Test
	public void ColunaStringRecebeJORGE_AMADOEDeveFormarDadosCorretamenteByteArray() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.string, (byte) 30);
		ColunaString col = new ColunaString("JORGE_AMADO", ud);
		
		byte[] b = {0,24, 			// tamanho da coluna
				00,0x4A, 00,0x4F, 00,0x52, 
				00,0x47, 00,0x45, 00,0x5F, 
				00,0x41, 00,0x4D, 00,0x41, 
				00,0x44, 00,0x4F};			// dados da coluna
		
		byte[] resultado = col.getByteArray();
		
		Assert.assertArrayEquals(b,resultado);		
	}
	
	@Test
	public void ColunaStringRecebe222222222222222222222222222222EDeveFormarDadosCorretamenteByteArray() throws IncorrectFormatException{
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.string, (byte) 30);
		ColunaString col = new ColunaString("222222222222222222222222222222", ud);
		
		byte[] b = {0,62, 			// tamanho da coluna
				00,0x32, 00,0x32, 00,0x32, 
				00,0x32, 00,0x32, 00,0x32, 
				00,0x32, 00,0x32, 00,0x32,
				00,0x32, 00,0x32, 00,0x32,
				00,0x32, 00,0x32, 00,0x32,
				00,0x32, 00,0x32, 00,0x32,
				00,0x32, 00,0x32, 00,0x32,
				00,0x32, 00,0x32, 00,0x32,
				00,0x32, 00,0x32, 00,0x32,
				00,0x32, 00,0x32, 00,0x32};			// dados da coluna
		
		byte[] resultado = col.getByteArray();
		
		Assert.assertArrayEquals(b,resultado);		
	}
}
