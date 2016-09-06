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
	
	public Arquivo(BlocoControle blocoControle){
		
		this.blocoControle = blocoControle;
		
//		blocos = new ArrayList<Bloco>();
		
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
