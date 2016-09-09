package gerenciador.TestesArquivo.byteArrayTools;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.utils.ByteArrayTools;

public class SubArraysTeste {

	@Test
	public void DeveReceberArray12345ERetornar12(){
		byte[] t = {1,2,3,4,5};
		byte[] r = ByteArrayTools.subArray(t, 2);
		
		byte[] asert = {1,2}; 
		
		Assert.assertArrayEquals(asert, r);
	}
	
	@Test
	public void DeveReceberArray12345ERetornar1(){
		byte[] t = {1,2,3,4,5};
		byte[] r = ByteArrayTools.subArray(t, 1);
		
		byte[] asert = {1}; 
		
		Assert.assertArrayEquals(asert, r);
	}
	
	@Test
	public void DeveReceberArray12345ERetornar12345(){
		byte[] t = {1,2,3,4,5};
		byte[] r = ByteArrayTools.subArray(t, 5);
		
		byte[] asert = {1,2,3,4,5}; 
		
		Assert.assertArrayEquals(asert, r);
	}
	
	@Test
	public void DeveReceberArray12345ERetornar2(){
		byte[] t = {1,2,3,4,5};
		byte[] r = ByteArrayTools.subArray(t, 1, 1);
		
		byte[] asert = {2}; 
		
		Assert.assertArrayEquals(asert, r);
	}
}
