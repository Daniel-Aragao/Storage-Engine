package gerenciador.TestesArquivo.BlocoControle;

import org.junit.Assert;
import org.junit.Test;

import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.ETipoColuna;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class BlocoControleTeste {

	@Test
	public void deveReceberPropriedadesEGetByteArrayDeveVirCorreto() throws IncorrectFormatException{
		String []props = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		BlocoControle bc = new BlocoControle(props, (byte)1);
		
		byte[] a = {
				//header
				1,
				0,16,0,
				0,
				0,0,0,1,
				0,64,
				//descritores
				0,0,0,0,0,0,0,0,0,0,00,0x43, 00,0x4F, 00,0x44, 00,0x5F, 00,0x41, 00,0x55,
				00,0x54, 00,0x48, 00,0x4F, 00,0x52, GerenciadorArquivos.CARACTERE_INTEIRO,5,
				0,0,0,0,0,0,0,0,00,0x4E, 00,0x41, 00,0x4D, 00,0x45, 00,0x5F, 00,0x41, 00,0x55, 00,
				0x54, 00,0x48, 00,0x4F, 00,0x52, GerenciadorArquivos.CARACTERE_STRING,100};
		
		byte [] b = bc.getByteArray();
		
		byte[] result = ByteArrayTools.subArray(b,75);
		byte[] result2 = ByteArrayTools.subArray(b,75, b.length - 75);
		
		Assert.assertArrayEquals(a, result);
		Assert.assertArrayEquals(new byte[b.length - 75], result2);
		
	}
	
	@Test
	public void deveByteArrayRetornarPropriedadesFromByteArrayCorreto() throws IncorrectFormatException{
		String []props = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
		BlocoControle bc = new BlocoControle(props, (byte)1);
		
		BlocoControle result = new BlocoControle(bc.getByteArray());
		
		// Descritores
		Assert.assertEquals("COD_AUTHOR", result.getDescritor().getUnidadeDescricao(0).getNome());
		Assert.assertEquals("NAME_AUTHOR", result.getDescritor().getUnidadeDescricao(1).getNome());
		
		Assert.assertEquals(5, result.getDescritor().getUnidadeDescricao(0).getTamanho());
		Assert.assertEquals(100, result.getDescritor().getUnidadeDescricao(1).getTamanho());
		
		Assert.assertEquals(ETipoColuna.inteiro, result.getDescritor().getUnidadeDescricao(0).getTipo());
		Assert.assertEquals(ETipoColuna.string, result.getDescritor().getUnidadeDescricao(1).getTipo());
		
		// Header
		
		Assert.assertEquals(1, result.getHeader().getContainerId());
		Assert.assertEquals(BlocoControle.TAMANHO_BLOCO, result.getHeader().getSizeBloco());
		Assert.assertEquals(0, result.getHeader().getStatusContainer());
		Assert.assertEquals(1, result.getHeader().getProxBlocoLivre());
		Assert.assertEquals(64, result.getHeader().getSizeDescritor());
		
	}
	
}
