package gerenciador.TestesArquivo.Bloco;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.DadosBloco;
import gerenciador.arquivos.blocos.IDados;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;

public class DadosBlocoTeste {
	/*
	101|LUIZ RUFFATO|
	20|JORGE AMADO|
	13|CHARLES DICKENS|
	 **/
	
	private Tupla getTupla(int index) throws IncorrectFormatException{
		Descritor d = getDescritor();
		
		String props[][] = {
					{"101", "LUIZ_RUFFATO"},
					{"20", "JORGE_AMADO"},
					{"13", "CHARLES_DICKENS"}
				};
		
		RowId t = new RowId((byte) 0, 0, 0); 
		return new Tupla(props[index], t, d);
	}
	
	private Descritor getDescritor() throws IncorrectFormatException{
		String descs[] = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		return new Descritor(descs); 
	}
	
	@Test
	public void DeveReceberUmaTuplaEForma_laCorretamente() throws IncorrectFormatException{
		
		Tupla tupla = getTupla(0);
		
		IDados db = new DadosBloco(getDescritor());
		db.addTupla(tupla);
		
		
		Assert.assertEquals(4 + 6 + 26,db.getTupla(0).getSize());
		Assert.assertEquals(2,db.getTupla(0).getColunas().size());
	}
	
	@Test
	public void DeveReceberDuasTuplaEForma_laCorretamente() throws IncorrectFormatException{
				
		IDados db = new DadosBloco(getDescritor());
		db.addTupla(getTupla(0));
		db.addTupla(getTupla(1));
		
		Assert.assertEquals(4 + 6 + 26,db.getTupla(0).getSize());
		Assert.assertEquals(2,db.getTupla(0).getColunas().size());
		
		Assert.assertEquals(4 + 6 + 24,db.getTupla(1).getSize());
		Assert.assertEquals(2,db.getTupla(1).getColunas().size());
	}
	
	@Test
	public void DeveReceberUmaTuplaERetornarByteArrayCorreto() throws IncorrectFormatException{
		
		DadosBloco db = new DadosBloco(getDescritor());
		db.addTupla(getTupla(0));
		
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
		
		Assert.assertArrayEquals(correto, db.getByteArray());
	}
	
	@Test
	public void DeveReceberDuasTuplaERetornarByteArrayCorreto() throws IncorrectFormatException{
		
		DadosBloco db = new DadosBloco(getDescritor());
		db.addTupla(getTupla(0));
		db.addTupla(getTupla(1));
		
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
				00,0x41, 00,0x54, 00,0x4F,
				//inicio da tupla
				0,0,0,34,
				// 101
				0,6,
				0,0,0,(byte) 20,
				//JORGE_AMADO
				0,24, 			
				00,0x4A, 00,0x4F, 00,0x52, 
				00,0x47, 00,0x45, 00,0x5F, 
				00,0x41, 00,0x4D, 00,0x41, 
				00,0x44, 00,0x4F};		
		
		Assert.assertArrayEquals(correto, db.getByteArray());
	}
	
	@Test
	public void DeveReceberByteArrayUmaTuplaEForma_laCorretamente() throws IncorrectFormatException{
		
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
		RowId tupleId = new RowId((byte) 0, 0, 0);
		IDados db = new DadosBloco(correto, tupleId, getDescritor());
		
		
		Assert.assertEquals(4 + 6 + 26,db.getTupla(0).getSize());
		Assert.assertEquals(2,db.getTupla(0).getColunas().size());
	}
	@Test
	public void DeveReceberByteArrayDuasTuplaEForma_laCorretamente() throws IncorrectFormatException{
				
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
				00,0x41, 00,0x54, 00,0x4F,
				//inicio da tupla
				0,0,0,34,
				// 101
				0,6,
				0,0,0,(byte) 20,
				//JORGE_AMADO
				0,24, 			
				00,0x4A, 00,0x4F, 00,0x52, 
				00,0x47, 00,0x45, 00,0x5F, 
				00,0x41, 00,0x4D, 00,0x41, 
				00,0x44, 00,0x4F};
		

		RowId tupleId = new RowId((byte) 0, 0, 0);
		IDados db = new DadosBloco(correto, tupleId, getDescritor());
		
		Assert.assertEquals(4 + 6 + 26,db.getTupla(0).getSize());
		Assert.assertEquals(2,db.getTupla(0).getColunas().size());
		
		Assert.assertEquals(4 + 6 + 24,db.getTupla(1).getSize());
		Assert.assertEquals(2,db.getTupla(1).getColunas().size());
	}
}
