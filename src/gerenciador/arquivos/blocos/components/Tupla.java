package gerenciador.arquivos.blocos.components;

import java.util.ArrayList;

import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class Tupla implements IBinarizable<Tupla>{

	private int size;
	
	private ArrayList<Coluna> colunas;
	
	public Tupla(byte[] bytes)throws IncorrectFormatException{
		setSize(bytes.length);
		
		setColunas(new ArrayList<>());
		
//		byte[] dado =
		for(int i = 0; i < size; i ++){
			
		}
	}
	
	@Override
	public byte[] getByteArray() {
		byte[] retorno = ByteArrayTools.intToByteArray(size);
		
		for(Coluna c : colunas){
			retorno = ByteArrayTools.concatArrays(retorno, c.getByteArray());
		}
		
		return retorno;
	}

	public int getSize() {
		return size;
	}

	private void setSize(int size) {
		this.size = size;
	}

	public ArrayList<Coluna> getColunas() {
		return colunas;
	}

	private void setColunas(ArrayList<Coluna> colunas) {
		this.colunas = colunas;
	}

}
