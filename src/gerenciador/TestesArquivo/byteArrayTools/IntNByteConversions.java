package gerenciador.TestesArquivo.byteArrayTools;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.utils.ByteArrayTools;

public class IntNByteConversions {

	// int to byte array
	@Test
	public void passar3ERetornar0003(){
		int a = 3;
		byte[] b = {0,0,0,3};
		
		byte[] result = ByteArrayTools.intToByteArray(a);
		
		Assert.assertArrayEquals(b, result);				
	}
	
	@Test
	public void passar127ERetornar000127(){
		int a = 127;
		byte[] b = {0,0,0,127};
		
		byte[] result = ByteArrayTools.intToByteArray(a);
		
		Assert.assertArrayEquals(b, result);				
	}
	
	@Test
	public void passar2048ERetornar0080(){
		int a = 2048;
		byte[] b = {0,0,8,0};
		
		byte[] result = ByteArrayTools.intToByteArray(a);
		
		Assert.assertArrayEquals(b, result);				
	}
	
	@Test
	public void passar32768ERetornar00_1280(){
		int a = 32768;
		byte[] b = {0,0,-128,0};
		
		byte[] result = ByteArrayTools.intToByteArray(a);
		
		Assert.assertArrayEquals(b, result);				
	}
	
	@Test
	public void passar32520ERetornar001278(){
		int a = 32520;
		byte[] b = {0,0,127,8};
		
		byte[] result = ByteArrayTools.intToByteArray(a);
		
		Assert.assertArrayEquals(b, result);				
	}
	
	@Test
	public void passar134744072ERetornar8888(){
		int a = 134744072;
		byte[] b = {8,8,8,8};
		
		byte[] result = ByteArrayTools.intToByteArray(a);
		
		Assert.assertArrayEquals(b, result);				
	}
	
	@Test
	public void passar6ERetornar0006(){
		int a = 6;
		byte[] b = {0,0,0,6};
		
		byte[] result = ByteArrayTools.intToByteArray(a);
		
		Assert.assertArrayEquals(b, result);				
	}
	
	//byte array to int
	
	@Test
	public void passar0008ERetornar8(){
		byte[] a = {0,0,0,8};
		int b = 8;
		
		int result = ByteArrayTools.byteArrayToInt(a);
		
		Assert.assertEquals(b, result);
	}
	
	@Test
	public void passar000255ERetornar255(){
		byte[] a = {0,0,0,(byte) 255};
		int b = 255;
		
		int result = ByteArrayTools.byteArrayToInt(a);
		
		Assert.assertEquals(b, result);
	}
	
	@Test
	public void passar0088ERetornar2056(){
		byte[] a = {0,0,8,8};
		int b = 2056;
		
		int result = ByteArrayTools.byteArrayToInt(a);
		
		Assert.assertEquals(b, result);
	}
	
	@Test
	public void passar8888ERetornar134744072(){
		byte[] a = {8,8,8,8};
		int b = 134744072;
		
		int result = ByteArrayTools.byteArrayToInt(a);
		
		Assert.assertEquals(b, result);
	}
	
	@Test
	public void passar888ERetornar526344(){
		byte[] a = {8,8,8};
		int b = 526344;
		
		int result = ByteArrayTools.byteArrayToInt(a);
		
		Assert.assertEquals(b, result);
	}
	
	@Test
	public void passar88ERetornar2056(){
		byte[] a = {8,8};
		int b = 2056;
		
		int result = ByteArrayTools.byteArrayToInt(a);
		
		Assert.assertEquals(b, result);
	}
	
	@Test
	public void passar200ERetornar200(){
		byte[] a = {(byte) 200};
		int b = 200;
		
		int result = ByteArrayTools.byteArrayToInt(a);
		
		Assert.assertEquals(b, result);
	}
}
