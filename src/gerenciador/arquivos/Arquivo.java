package gerenciador.arquivos;

import java.util.ArrayList;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.BlocoControle;
import gerenciador.arquivos.exceptions.ByteArrayIncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;

public class Arquivo implements IBinarizable<Arquivo>{
	@SuppressWarnings("unused")
	private BlocoControle blocoControle;
	@SuppressWarnings("unused")
	private ArrayList<Bloco> blocos;
	
	private Arquivo(byte[] bytes)throws ByteArrayIncorrectFormatException{
		
		if(BlocoControle.MIN_ARRAY_SIZE > bytes.length){
			throw new ByteArrayIncorrectFormatException();
		}
		blocoControle = new BlocoControle(bytes);
		blocos = new ArrayList<Bloco>();
		//blocoControle.getSize()
		/*esse for vai ter que avança de bloco em bloco no bytearray,
		 * logo não começa do minarraysize e sim do byte após o último 
		 * byte do bloco de controle e assim também avança em vez de i++*/
		
//		for(int i = BlocoControle.MIN_ARRAY_SIZE; i < bytes.length; i++){
//			Bloco bloco = new Bloco();
//			blocos.add(bloco)
//		}
		
	}
	
	@Override
	public byte[] getByteArray() {
		// TODO Auto-generated method stub
		return null;
	}
}
