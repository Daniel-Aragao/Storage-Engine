package gerenciador.TestesArquivo.Bloco;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.GerenciadorBuffer;
import gerenciador.RowId;
import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.Coluna;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.indice.blocos.Chave;
import gerenciador.indice.blocos.Node;
import gerenciador.utils.ByteArrayTools;

public class NodeTeste {
	private Chave getTupla(int index) throws IncorrectFormatException{
		Descritor d = getDescritor();
		
		String props[][] = {
					{"101", "LUIZ_RUFFATO"},
					{"20", "JORGE_AMADO"},
					{"13", "CHARLES_DICKENS"}
				};
		
		RowId t = new RowId((byte) 0, 0, 0);
		Tupla tupla = new Tupla(props[index], t, d);
		Coluna[] colunas = new Coluna[d.getDescritores().size()];
		int i = 0;
		for(Coluna c : tupla.getColunas()){
			colunas[i] = c;
			i++;
		}
		return new Chave(colunas, null, d, tupla.getTupleId());
	}
	
	private Descritor getDescritor() throws IncorrectFormatException{
		String descs[] = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		return new Descritor(descs); 
	}
	@Test
	public void DeveReceber1_1_dado_descEFormarDadosCorretamente() throws IncorrectFormatException{
		IBloco bloco = new Node((byte) 1,1,ETipoBlocoArquivo.indices, getDescritor());
		
		Assert.assertEquals(1, bloco.getHeader().getBlocoId());
		Assert.assertEquals(1, bloco.getHeader().getContainerId());
		Assert.assertEquals(12, bloco.getHeader().getBytesUsados());
		Assert.assertEquals(ETipoBlocoArquivo.indices, bloco.getHeader().getTipo());		
	}
	@Test
	public void DeveReceber1_1_dado_descEFormarDadosCorretamenteByteArray() throws IncorrectFormatException{
		Bloco bloco = new Bloco((byte) 1,1,ETipoBlocoArquivo.dados, getDescritor());
		byte[] ba = {
				1,
				0,0,1,
				ETipoBlocoArquivo.dados.getValor(),
				0,0,8
				,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
		};
		
		byte[] resultado = ByteArrayTools.subArray(bloco.getByteArray(),0,30);
		
		Assert.assertArrayEquals(ba, resultado);
	}
	@Test
	public void DeveReceberTuplaFormarDadosCorretamenteByteArray() throws IncorrectFormatException{
		Node bloco = new Node((byte) 1,1,ETipoBlocoArquivo.indices, getDescritor());
		bloco.addTupla(getTupla(0));
		
		byte[] ba = {
				1,
				0,0,1,
				ETipoBlocoArquivo.indices.getValor(),
				0,0,12,0,37,0,0,
				// inicio da chave
				0,0,0,0,0,0,0,0,
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
		
		byte[] resultado = ByteArrayTools.subArray(bloco.getByteArray(),0, 56);
		
		Assert.assertArrayEquals(ba, resultado);
	}
	
	@Test
	public void DeveReceberDuasTuplaFormarDadosCorretamenteByteArray() throws IncorrectFormatException{
		Node bloco = new Node((byte) 1,1,ETipoBlocoArquivo.indices, getDescritor());
		bloco.addTupla(getTupla(0));
		bloco.addTupla(getTupla(1));
		byte[] ba = {
				1,
				0,0,1,
				ETipoBlocoArquivo.indices.getValor(),
				0,0,12,0,37,0,0,				
				// inicio da tupla
				0,0,0,0,0,0,0,0,
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
				0,0,0,0,0,0,0,0,
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
		
		byte[] resultado = ByteArrayTools.subArray(bloco.getByteArray(),0, 98);
		
		Assert.assertArrayEquals(ba, resultado);
	}
	
	@Test
	public void DeveReceber_ByteArray_e_devolver_byteArrayigual() throws IncorrectFormatException{
		Node bloco1 = new Node((byte) 1,1,ETipoBlocoArquivo.indices, getDescritor());
		bloco1.addTupla(getTupla(0));
		bloco1.addTupla(getTupla(1));
		
		Node bloco = new Node(bloco1.getByteArray(), getDescritor());
		bloco.addTupla(getTupla(0));
		bloco.addTupla(getTupla(1));
		
		Assert.assertArrayEquals(bloco1.getByteArray(), bloco.getByteArray());	
	}
	
}
