package gerenciador.arquivos.blocos.components;

import java.util.ArrayList;

import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class DadosBloco  implements IBinarizable<DadosBloco>{
	
	private ArrayList<Tupla> tuplas;
	
	public DadosBloco() {
		setTuplas(new ArrayList<Tupla>());
	}

	@Override
	public byte[] getByteArray() {
		byte[] retorno = new byte[0];
		
		for(Tupla t : tuplas){
			retorno = ByteArrayTools.concatArrays(retorno, t.getByteArray());
		}
		
		return retorno;
	}

	public ArrayList<Tupla> getTuplas() {
		return tuplas;
	}

	public void setTuplas(ArrayList<Tupla> tuplas) {
		this.tuplas = tuplas;
	}
	
}
