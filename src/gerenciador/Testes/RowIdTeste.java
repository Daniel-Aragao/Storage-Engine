package gerenciador.Testes;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.RowId;
import gerenciador.arquivos.exceptions.IncorrectFormatException;

public class RowIdTeste {
	@Test
	public void passarfrom1_4_3ereceberget1_4_3() throws IncorrectFormatException{
		byte[] a = {1,0,0,4,0,0,0,3};
		
		RowId result = new RowId(a);
		
		Assert.assertArrayEquals(a, result.getByteArray());
	}
	
	@Test
	public void passarparam1_4_3ereceber1_4_3() throws IncorrectFormatException{
		byte[] a = {1,0,0,4,0,0,0,3};
		
		RowId result = new RowId((byte) 1,4,3);
		
		Assert.assertArrayEquals(a, result.getByteArray());
	}
	
	@Test
	public void passarfrom1_4_3ereceberparam1_4_3() throws IncorrectFormatException{
		byte[] a = {1,0,0,4,0,0,0,3};
		
		RowId result = new RowId(a);
		
		Assert.assertEquals(1, result.getContainerId());
		Assert.assertEquals(4, result.getBlocoId());
		Assert.assertEquals(3, result.getOffSet());
	}
}
