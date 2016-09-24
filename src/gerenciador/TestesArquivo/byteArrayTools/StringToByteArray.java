package gerenciador.TestesArquivo.byteArrayTools;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.utils.ByteArrayTools;

public class StringToByteArray {
	@Test
	public void passar_ola_receber(){
		String a = "ola";
		byte[] b = {0,0x6F, 0,0x6C, 0,0x61};
		
		byte[] result = ByteArrayTools.stringToByteArray(a);
		
		Assert.assertArrayEquals(b, result);
	}
	
	@Test
	public void passar_luiz_ruffato_receber(){
		String a = "LUIZ RUFFATO";
		byte[] b = {0, 0x4C, 0, 0x55, 0, 0x49, 0, 0x5A, 0, 0x20, 
				0, 0x52, 0, 0x55, 0, 0x46, 0, 0x46, 0, 0x41, 0, 0x54, 0, 0x4F};
		
		byte[] result = ByteArrayTools.stringToByteArray(a);
		
		Assert.assertArrayEquals(b, result);
	}
	
	@Test
	public void passar_luiz_ruffato_receberComOffset(){
		String a = "LUIZ RUFFATO";
		byte[] b = {0,0,0,0,0,0, 0, 0x4C, 0, 0x55, 0, 0x49, 0, 0x5A, 0, 0x20, 
				0, 0x52, 0, 0x55, 0, 0x46, 0, 0x46, 0, 0x41, 0, 0x54, 0, 0x4F};
		
		byte[] result = ByteArrayTools.stringToByteArray(a, 30);
		
		Assert.assertArrayEquals(b, result);
	}
	
	@Test
	public void passarArrayDeLuizRuffatoEReceberEleMesmo(){
		byte[] a = {0,0, 0,0, 0,0, 0,0x4C, 0, 0x55, 0, 0x49, 0, 0x5A, 0, 0x20, 
				0, 0x52, 0, 0x55, 0, 0x46, 0, 0x46, 0, 0x41, 0, 0x54, 0, 0x4F};
		String b = "LUIZ RUFFATO";
		
		String result = ByteArrayTools.byteArrayToString(a);
		
		Assert.assertEquals(b, result);
	}
	
	@Test
	public void passarArrayDeLuizRuffatoEReceberEleMesmoSemOffset(){
		byte[] a = {0,0x4C, 0, 0x55, 0, 0x49, 0, 0x5A, 0, 0x20, 
				0, 0x52, 0, 0x55, 0, 0x46, 0, 0x46, 0, 0x41, 0, 0x54, 0, 0x4F};
		String b = "LUIZ RUFFATO";
		
		String result = ByteArrayTools.byteArrayToString(a);
		
		Assert.assertEquals(b, result);
	}
	
	@Test
	public void passarStringCumpridaEReceberElaTruncada(){
//		004C 0055 0049 / 005A = LUI/Z
		
		String a = "LUIZ";
		byte[] b = {0, 0x4C, 0, 0x55, 0, 0x49};
		
		byte[] result = ByteArrayTools.stringToByteArray(a, 6);
		
		Assert.assertArrayEquals(b, result);
	}
	
	@Test
	public void converterEDesconverterStringComCCedilha(){
		String alcapao = "Alçapão";
		
		byte[] bytes = ByteArrayTools.stringToByteArray(alcapao);
		String resultado = ByteArrayTools.byteArrayToString(bytes);
		
		Assert.assertEquals(alcapao,resultado);
	}
}
