package gerenciador.arquivos.blocos.components;

import java.util.ArrayList;

import gerenciador.arquivos.exceptions.ByteArrayIncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;

public class Tupla implements IBinarizable<Tupla>{

	private int size;
	
	private ArrayList<Coluna> colunas;
	
	public Tupla(byte[] bytes)throws ByteArrayIncorrectFormatException{
		setSize(bytes.length);
		
		setColunas(new ArrayList<>());
		
//		byte[] dado =
		for(int i = 0; i < size; i ++){
			
		}
	}
	
	@Override
	public byte[] getByteArray() {
		// TODO Auto-generated method stub
		return null;
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
