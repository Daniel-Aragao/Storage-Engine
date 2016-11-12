package gerenciador.TestesArquivo.Bloco;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.Coluna;
import gerenciador.arquivos.blocos.ColunaInt;
import gerenciador.arquivos.blocos.ColunaString;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.ITupla;

public class TuplaTeste {
	/*
	101|LUIZ RUFFATO|
	20|JORGE AMADO|
	13|CHARLES DICKENS|
	 **/
	
	private Tupla getTupla() throws IncorrectFormatException{
		Descritor d = getDescritor();
		
		String props[] = {"101", "LUIZ_RUFFATO"};
		
		RowId t = new RowId((byte) 0, 0, 0); 
		return new Tupla(props, t, d);
	}
	
	private Descritor getDescritor() throws IncorrectFormatException{
		String descs[] = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		return new Descritor(descs); 
	}
	
	@Test
	public void TuplaRecebe101_LUIZ_RUFFATO_EDeveTerSize() throws IncorrectFormatException{
		
		ITupla tupla = getTupla();
		
		Assert.assertEquals(4 + 6 + 26,tupla.getSize());
		Assert.assertEquals(2,tupla.getColunas().size());		
		
		
	}
	@Test 
	public void TuplaCreateColunaRecebe101DevolveColunaInt() throws IncorrectFormatException{
		
		ITupla tupla = getTupla();
		
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.inteiro, (byte) 5);
		String prop = "101";
		
		Coluna col = tupla.createColuna(prop, ud);
		
		Assert.assertTrue(col.getClass() == ColunaInt.class);
	}
	
	@Test 
	public void TuplaCreateColunaRecebeLUIZ_RUFFATODevolveColunaString() throws IncorrectFormatException{
		
		ITupla tupla = getTupla();
		
		UnidadeDescricao ud = new UnidadeDescricao("tanto faz", ETipoColuna.string, (byte) 12);
		String prop = "LUIZ_RUFFATO";
		
		Coluna col = tupla.createColuna(prop, ud);
		
		Assert.assertTrue(col.getClass() == ColunaString.class);
	}
	
	@Test
	public void TuplaRecebe101_LUIZ_RUFFATO_ERetornaByteArrayCorreto() throws IncorrectFormatException{
		byte[] correto = {
					// inicio da tupla
					0,0,0,36,
					// 101
					0,6,
					0,0,0,(byte) 101,
					// LUIZ_RUFFATO
					0,26,
					00,0x4C, 00,0x55, 00,0x49,
					00,0x5A, 00,0x5F, 00,0x52, 
					00,0x55, 00,0x46, 00,0x46, 
					00,0x41, 00,0x54, 00,0x4F
				};
		
		Tupla tupla = getTupla();
		
		Assert.assertArrayEquals(correto, tupla.getByteArray());
	}
	
	@Test
	public void TuplaRecebe101_LUIZ_RUFFATO_ERetornaDadosDasColunasCorretos() throws IncorrectFormatException{
		byte[] correto = {
					// inicio da tupla
					0,0,0,36,
					// 101
					0,6,
					0,0,0,(byte) 101,
					// LUIZ_RUFFATO
					0,26,
					00,0x4C, 00,0x55, 00,0x49,
					00,0x5A, 00,0x5F, 00,0x52, 
					00,0x55, 00,0x46, 00,0x46, 
					00,0x41, 00,0x54, 00,0x4F
				};
		
		ITupla tupla = getTupla();
		
		Assert.assertEquals(26, tupla.getColuna(1).getColumnSize());
		Assert.assertEquals("LUIZ_RUFFATO",tupla.getColuna(1).getDado());
		
		
		Assert.assertEquals(6,tupla.getColuna(0).getColumnSize());
		Assert.assertEquals(101,(int)tupla.getColuna(0).getDado());	
	}
	
	
	@Test
	public void TuplaRecebeByteArray_ERetorna_101_LUIZ_RUFFATO() throws IncorrectFormatException{
		byte[] correto = {
					// inicio da tupla
					0,0,0,36,
					// 101
					0,6,
					0,0,0,(byte) 101,
					// LUIZ_RUFFATO
					0,26,
					00,0x4C, 00,0x55, 00,0x49,
					00,0x5A, 00,0x5F, 00,0x52, 
					00,0x55, 00,0x46, 00,0x46, 
					00,0x41, 00,0x54, 00,0x4F
				};
		RowId t = new RowId((byte) 0, 0, 0); 
		ITupla tupla = new Tupla(correto, t, getDescritor());
		
		Assert.assertEquals(6,tupla.getColuna(0).getColumnSize());
		Assert.assertEquals(101,(int)tupla.getColuna(0).getDado());	
		
		Assert.assertEquals(26, tupla.getColuna(1).getColumnSize());
		Assert.assertEquals("LUIZ_RUFFATO",tupla.getColuna(1).getDado());		
	}
	
	@Test
	public void TuplaRecebeByteArray_ERetorna_101_LUIZ_RUFFATOByteArray() throws IncorrectFormatException{
		byte[] correto = {
					// inicio da tupla
					0,0,0,36,
					// 101
					0,6,
					0,0,0,(byte) 101,
					// LUIZ_RUFFATO
					0,26,
					00,0x4C, 00,0x55, 00,0x49,
					00,0x5A, 00,0x5F, 00,0x52, 
					00,0x55, 00,0x46, 00,0x46, 
					00,0x41, 00,0x54, 00,0x4F
				};
		RowId t = new RowId((byte) 0, 0, 0); 
		Tupla tupla = new Tupla(correto,t, getDescritor());
		
		Assert.assertArrayEquals(correto, tupla.getByteArray());
	}
	
	@Test(expected=IncorrectFormatException.class)
	public void TuplaRecebeByteArray_TrocadoELancarExcecao() throws IncorrectFormatException{
		byte[] incorreto = {
					// inicio da tupla
					0,0,0,36,
					// LUIZ_RUFFATO
					0,26,
					00,0x4C, 00,0x55, 00,0x49,
					00,0x5A, 00,0x5F, 00,0x52, 
					00,0x55, 00,0x46, 00,0x46, 
					00,0x41, 00,0x54, 00,0x4F,
					// 101
					0,6,
					0,0,0,(byte) 101
				};
		
		RowId t = new RowId((byte) 0, 0, 0); 
		ITupla tupla = new Tupla(incorreto,t, getDescritor());
	}
}



































