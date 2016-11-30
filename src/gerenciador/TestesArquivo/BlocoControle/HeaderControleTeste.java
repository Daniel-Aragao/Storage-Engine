package gerenciador.TestesArquivo.BlocoControle;

import org.junit.Test;

import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.HeaderControle;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import org.junit.Assert;

public class HeaderControleTeste {

	@Test
	public void DeveReceber1e64eMontarOHeaderCorretamente() throws IncorrectFormatException{
		HeaderControle hc = new HeaderControle("Author",(byte)1,(short) 64);
		
		
		Assert.assertEquals(1, hc.getContainerId());
		Assert.assertEquals(BlocoControle.TAMANHO_BLOCO, hc.getSizeBloco());
		Assert.assertEquals(1, hc.getProxBlocoLivre());
		Assert.assertEquals(0, hc.getStatusContainer());
		Assert.assertEquals(64, hc.getSizeDescritor());
	}
	
	@Test
	public void DeveReceber1e64EReceberByteArrayBemFormado() throws IncorrectFormatException{
		HeaderControle hc = new HeaderControle("Author", (byte)1,(short) 64);
		
		byte[] b = {1,
				0,16,0,
				0,
				0,0,0,1,
				0,64,
				0,0,0,0,0,0,0,0,0,0x41,0,0x75,0,0x74,0,0x68,0,0x6f,0,0x72,
				0,0,0,0,0,0,0,0,0,0,0,};		
		
		byte[]result = hc.getByteArray();
		
		Assert.assertArrayEquals(b, result);
	}
	@Test
	public void DeveReceber200e4096EReceberByteArrayBemFormado() throws IncorrectFormatException{
		HeaderControle hc = new HeaderControle("Author", (byte)200,(short) 4096);
		
		byte[] b = {(byte) 200,
				0,16,0,
				0,
				0,0,0,1,
				16,0,
				0,0,0,0,0,0,0,0,0,0x41,0,0x75,0,0x74,0,0x68,0,0x6f,0,0x72,
				0,0,0,0,0,0,0,0,0,0,0,};		
		
		byte[]result = hc.getByteArray();
		
		Assert.assertArrayEquals(b, result);
	}
	
	@Test
	public void receberByteArrayeFormaHeaderCorretamente() throws IncorrectFormatException{
		byte[] b = {1,
				0,16,0,
				0,
				0,0,8,8,
				0,64,
				0,0,0,0,0,0,0,0,0,0x41,0,0x75,0,0x74,0,0x68,0,0x6f,0,0x72,
				0,0,0,0,0,0,0,0,0,0,0,};	
		HeaderControle hc = new HeaderControle(b);
		
		
		Assert.assertEquals(1, hc.getContainerId());
		Assert.assertEquals(BlocoControle.TAMANHO_BLOCO, hc.getSizeBloco());
		Assert.assertEquals(0, hc.getStatusContainer());
		Assert.assertEquals(2056, hc.getProxBlocoLivre());
		Assert.assertEquals(64, hc.getSizeDescritor());
	}
}
