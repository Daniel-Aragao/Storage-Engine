package gerenciador.TestesArquivo.Bloco;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.enums.ETipoBloco;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.utils.ByteArrayTools;

public class BlocoTeste {
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
	public void DeveReceber1_1_dado_descEFormarDadosCorretamente() throws IncorrectFormatException{
		IBloco bloco = new Bloco((byte) 1,1,ETipoBloco.dados, getDescritor());
		
		Assert.assertEquals(1, bloco.getHeader().getBlocoId());
		Assert.assertEquals(1, bloco.getHeader().getContainerId());
		Assert.assertEquals(8, bloco.getHeader().getBytesUsados());
		Assert.assertEquals(ETipoBloco.dados, bloco.getHeader().getTipo());		
	}
	
	@Test
	public void DeveReceber1_1_dado_descEFormarDadosCorretamenteByteArray() throws IncorrectFormatException{
		Bloco bloco = new Bloco((byte) 1,1,ETipoBloco.dados, getDescritor());
		byte[] ba = {
				1,
				0,0,1,
				ETipoBloco.dados.getValor(),
				0,0,8
				,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
		};
		
		byte[] resultado = ByteArrayTools.subArray(bloco.getByteArray(),0,30);
		
		Assert.assertArrayEquals(ba, resultado);
	}
	
	@Test
	public void DeveReceberTuplaFormarDadosCorretamenteByteArray() throws IncorrectFormatException{
		Bloco bloco = new Bloco((byte) 1,1,ETipoBloco.dados, getDescritor());
		bloco.addTupla(getTupla(0));
		
		byte[] ba = {
				1,
				0,0,1,
				ETipoBloco.dados.getValor(),
				0,0,44,				
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
		
		byte[] resultado = ByteArrayTools.subArray(bloco.getByteArray(),0,44);
		
		Assert.assertArrayEquals(ba, resultado);
	}
	@Test
	public void DeveReceberDuasTuplaFormarDadosCorretamenteByteArray() throws IncorrectFormatException{
		Bloco bloco = new Bloco((byte) 1,1,ETipoBloco.dados, getDescritor());
		bloco.addTupla(getTupla(0));
		bloco.addTupla(getTupla(1));
		byte[] ba = {
				1,
				0,0,1,
				ETipoBloco.dados.getValor(),
				0,0,78,				
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
				// 20
				0,6,
				0,0,0,(byte) 20,
				//JORGE_AMADO
				0,24, 			
				00,0x4A, 00,0x4F, 00,0x52, 
				00,0x47, 00,0x45, 00,0x5F, 
				00,0x41, 00,0x4D, 00,0x41, 
				00,0x44, 00,0x4F};	
		
		byte[] resultado = ByteArrayTools.subArray(bloco.getByteArray(),0,78);
		
		Assert.assertArrayEquals(ba, resultado);
	}
	
	@Test
	public void DeveReceberDuasTuplasEOByteArrayRetornadoDeveSer0Do_i_78_EmDiante() throws IncorrectFormatException{
		Bloco bloco = new Bloco((byte) 1,1,ETipoBloco.dados, getDescritor());
		bloco.addTupla(getTupla(0));
		bloco.addTupla(getTupla(1));
		byte[] ba = {
				1,
				0,0,1,
				ETipoBloco.dados.getValor(),
				0,0,78,				
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
				// 20
				0,6,
				0,0,0,(byte) 20,
				//JORGE_AMADO
				0,24, 			
				00,0x4A, 00,0x4F, 00,0x52, 
				00,0x47, 00,0x45, 00,0x5F, 
				00,0x41, 00,0x4D, 00,0x41, 
				00,0x44, 00,0x4F};	
		
		byte[] resultado = ByteArrayTools.subArray(bloco.getByteArray(),0,78);
		
		Assert.assertArrayEquals(ba, resultado);
		Assert.assertArrayEquals(new byte[4018], ByteArrayTools.subArray(bloco.getByteArray(),78,4018));
	}
	@Test
	public void DeveReceberByteArrayEDeveConstruirHeaderCorretamente() throws IncorrectFormatException{
		byte[] ba = {
				1,
				0,0,1,
				ETipoBloco.dados.getValor(),
				0,0,78,				
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
				// 20
				0,6,
				0,0,0,(byte) 20,
				//JORGE_AMADO
				0,24, 			
				00,0x4A, 00,0x4F, 00,0x52, 
				00,0x47, 00,0x45, 00,0x5F, 
				00,0x41, 00,0x4D, 00,0x41, 
				00,0x44, 00,0x4F};	
		
		IBloco bloco = new Bloco(ba, getDescritor());
			
		//Testar construção do header
		Assert.assertEquals(1, bloco.getHeader().getBlocoId());
		Assert.assertEquals(1, bloco.getHeader().getContainerId());
		Assert.assertEquals(78, bloco.getHeader().getBytesUsados());
		Assert.assertEquals(ETipoBloco.dados, bloco.getHeader().getTipo());
		
		
		
	}
	@Test
	public void DeveReceberByteArrayTestarFormacaoDasTuplas() throws IncorrectFormatException{
		byte[] ba = {
				1,
				0,0,1,
				ETipoBloco.dados.getValor(),
				0,0,78,				
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
				// 20
				0,6,
				0,0,0,(byte) 20,
				//JORGE_AMADO
				0,24, 			
				00,0x4A, 00,0x4F, 00,0x52, 
				00,0x47, 00,0x45, 00,0x5F, 
				00,0x41, 00,0x4D, 00,0x41, 
				00,0x44, 00,0x4F};	
		
		Bloco bloco = new Bloco(ba, getDescritor());
		
		@SuppressWarnings("unused")
		byte[] resultado = ByteArrayTools.subArray(bloco.getByteArray(),0,78);

		
		//Testar construção das tuplas
		Assert.assertEquals(4 + 6 + 26,bloco.getDados().getTupla(0).getSize());
		Assert.assertEquals(2,bloco.getDados().getTupla(0).getColunas().size());
		
		Assert.assertEquals(4 + 6 + 24,bloco.getDados().getTupla(1).getSize());
		Assert.assertEquals(2,bloco.getDados().getTupla(1).getColunas().size());		
	}
	@Test
	public void DeveReceberByteArrayETestarIntegridadeDosDados() throws IncorrectFormatException{
		byte[] ba = {
				1,
				0,0,1,
				ETipoBloco.dados.getValor(),
				0,0,78,				
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
				// 20
				0,6,
				0,0,0,(byte) 20,
				//JORGE_AMADO
				0,24, 			
				00,0x4A, 00,0x4F, 00,0x52, 
				00,0x47, 00,0x45, 00,0x5F, 
				00,0x41, 00,0x4D, 00,0x41, 
				00,0x44, 00,0x4F};	
		
		IBloco bloco = new Bloco(ba, getDescritor());
		
		
		// Testar tuplas
		Assert.assertEquals(6,bloco.getDados().getTupla(0).getColuna(0).getColumnSize());
		Assert.assertEquals(101,(int)bloco.getDados().getTupla(0).getColuna(0).getDado());	
		Assert.assertEquals(26, bloco.getDados().getTupla(0).getColuna(1).getColumnSize());
		Assert.assertEquals("LUIZ_RUFFATO",bloco.getDados().getTupla(0).getColuna(1).getDado());
		
		Assert.assertEquals(6,bloco.getDados().getTupla(1).getColuna(0).getColumnSize());
		Assert.assertEquals(20,(int)bloco.getDados().getTupla(1).getColuna(0).getDado());	
		Assert.assertEquals(24, bloco.getDados().getTupla(1).getColuna(1).getColumnSize());
		Assert.assertEquals("JORGE_AMADO",bloco.getDados().getTupla(1).getColuna(1).getDado());
		
	}
	
	@Test
	public void DeveConterOtamanhoCertoDoDescritorAoAdicionarLinha() throws IncorrectFormatException{
		IBloco bloco = new Bloco((byte) 1,1,ETipoBloco.dados, getDescritor());
		
		Assert.assertEquals(1, bloco.getHeader().getBlocoId());
		Assert.assertEquals(1, bloco.getHeader().getContainerId());
		Assert.assertEquals(8, bloco.getHeader().getBytesUsados());
		Assert.assertEquals(ETipoBloco.dados, bloco.getHeader().getTipo());	
		
		bloco.addTupla(getTupla(2));
		bloco.addTupla(getTupla(2).getByteArray());
		
		Assert.assertEquals(1, bloco.getHeader().getBlocoId());
		Assert.assertEquals(1, bloco.getHeader().getContainerId());
		Assert.assertEquals(getTupla(2).getSize()*2 + 8, bloco.getHeader().getBytesUsados());
		Assert.assertEquals(92, bloco.getHeader().getBytesUsados());
		Assert.assertEquals(ETipoBloco.dados, bloco.getHeader().getTipo());
	}
	
}


































