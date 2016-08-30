package gerenciador.arquivos;

import java.util.ArrayList;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.exceptions.ByteArrayIncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class Arquivo implements IBinarizable<Arquivo>{
	private BlocoControle blocoControle;
	private ArrayList<Bloco> blocos;
	
	private Arquivo(byte[] bytes)throws ByteArrayIncorrectFormatException{
		
		if(BlocoControle.MIN_ARRAY_SIZE > bytes.length){
			throw new ByteArrayIncorrectFormatException("too small array");
		}else if(BlocoControle.MAX_ARRAY_SIZE < bytes.length){
			throw new ByteArrayIncorrectFormatException("too long array");
		}
		blocoControle = new BlocoControle(bytes);
		blocos = new ArrayList<Bloco>();
		//blocoControle.getSize()
		/*esse for vai ter que avan�a de bloco em bloco no bytearray,
		 * logo n�o come�a do minarraysize e sim do byte ap�s o �ltimo 
		 * byte do bloco de controle e assim tamb�m avan�a em vez de i++*/
		
//		for(int i = BlocoControle.MIN_ARRAY_SIZE; i < bytes.length; i++){
//			Bloco bloco = new Bloco();
//			blocos.add(bloco)
//		}
		
	}
	
	@Override
	public byte[] getByteArray() {
		byte[] blocosArray = blocoControle.getByteArray();
		
		for(Bloco bloco : blocos){
			blocosArray = ByteArrayTools.concatArrays(blocosArray, bloco.getByteArray());
		}
		
		return blocosArray;
	}
}
