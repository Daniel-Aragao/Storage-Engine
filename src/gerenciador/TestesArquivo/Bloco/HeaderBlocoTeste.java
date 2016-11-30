package gerenciador.TestesArquivo.Bloco;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.arquivos.blocos.HeaderBloco;
import gerenciador.arquivos.blocos.IHeader;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;

public class HeaderBlocoTeste {
	@Test
	public void DeveReceber11dadosERetornar1100(){
		IHeader hb = new HeaderBloco((byte) 1,1,ETipoBlocoArquivo.dados);
		
		Assert.assertEquals(1, hb.getBlocoId());
		Assert.assertEquals(1, hb.getContainerId());
		Assert.assertEquals(8, hb.getBytesUsados());
		Assert.assertEquals(ETipoBlocoArquivo.dados, hb.getTipo());
	}
	
	@Test
	public void DeveReceber11dadosERetornargetByteArrayCorreto(){
		HeaderBloco hb = new HeaderBloco((byte) 1,1,ETipoBlocoArquivo.dados);
		
		byte[] ba = {1,
				0,0,1,
				ETipoBlocoArquivo.dados.getValor(),
				0,0,8
		};
		
		Assert.assertArrayEquals(ba, hb.getByteArray());
	}
	
	@Test
	public void DeveReceber15_3557_dadosERetornargetByteArrayCorreto(){
		HeaderBloco hb = new HeaderBloco((byte) 15,3557,ETipoBlocoArquivo.dados);
		
		byte[] ba = {15,
				0,13,(byte) 229,
				ETipoBlocoArquivo.dados.getValor(),
				0,0,8
		};
		
		Assert.assertArrayEquals(ba, hb.getByteArray());
	}
	
	@Test
	public void DeveByteArrayERetornar15_3557(){
		byte[] ba = {15,
				0,13,(byte) 229,
				ETipoBlocoArquivo.dados.getValor(),
				0,0,0
		};
		
		HeaderBloco hb = new HeaderBloco(ba);
		
		Assert.assertArrayEquals(ba, hb.getByteArray());
		
		Assert.assertEquals(15, hb.getContainerId());
		Assert.assertEquals(3557, hb.getBlocoId());
		Assert.assertEquals(0, hb.getBytesUsados());
		Assert.assertEquals(ETipoBlocoArquivo.dados, hb.getTipo());
	}
	
	@Test
	public void DeveByteArrayERetornar200_888(){
		byte[] ba = {(byte) 200,
				8,8,8,
				ETipoBlocoArquivo.dados.getValor(),
				8,8,9
		};
		
		HeaderBloco hb = new HeaderBloco(ba);
		
		Assert.assertArrayEquals(ba, hb.getByteArray());
		
		Assert.assertEquals((byte)200, hb.getContainerId());
		Assert.assertEquals(526344, hb.getBlocoId());
		Assert.assertEquals(526345, hb.getBytesUsados());
		Assert.assertEquals(ETipoBlocoArquivo.dados, hb.getTipo());
	}
	
	
}



































