package gerenciador.TestesArquivo.byteArrayTools;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.utils.ByteArrayTools;

public class ConcatArraysTeste {

	@Test
	public void passar12_34ereceber1234(){
		byte[] a = {1,2};
		byte[] b = {3,4};
		
		byte[] c = {1,2,3,4};
		
		byte[] result = ByteArrayTools.concatArrays(a, b);
		
		Assert.assertArrayEquals(c, result);
	}
	
	@Test
	public void passar123456_retornar123456(){
		byte[] a = {1,2,3,4,5,6};
		byte[] b = new byte[0];
		
		byte[] c = {1,2,3,4,5,6};
		
		byte[] result = ByteArrayTools.concatArrays(a, b);
		
		Assert.assertArrayEquals(c, result);
	}
	@Test
	public void append25to13(){
		byte[] a = {2,5,0,0};
		byte[] b = {1,3};
		
		byte[] c = {2,5,1,3};
		
		ByteArrayTools.appendArrays(a, b,2);
		
		Assert.assertArrayEquals(c, a);
	}
	
	@Test
	public void append25to134(){
		byte[] a = {2,5,0,0,0};
		byte[] b = {1,3,4};
		
		byte[] c = {2,5,1,3,4};
		
		ByteArrayTools.appendArrays(a, b,2);
		
		Assert.assertArrayEquals(c, a);
	}
}
