package gerenciador.arquivos.blocos;

import java.util.ArrayList;

import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class Tupla implements IBinarizable<Tupla>{

	private int size;	
	private ArrayList<Coluna> colunas;
	
	
	public Tupla(String[] props, Descritor descritor)throws IncorrectFormatException{
		setColunas(new ArrayList<>());
		
		if (props.length != descritor.getNumberOfColumns()){
			throw new IncorrectFormatException("Número de colunas inseridas é diferente"
					+ "do número de colunas da tabela");
		}
		
		for(int i = 0; i < props.length; i++){
			UnidadeDescricao descricao = descritor.getUnidadeDescricao(i);
			
			Coluna coluna = createColuna(props[i], descricao);			
			
			colunas.add(coluna);
			this.size += coluna.getColumnSize();
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

	public ArrayList<Coluna> getColunas() {
		return colunas;
	}

	private void setColunas(ArrayList<Coluna> colunas) {
		this.colunas = colunas;
	}
	
	private Coluna createColuna(String prop, UnidadeDescricao descricao) throws IncorrectFormatException{
		Coluna coluna = null;
		
		switch (descricao.getTipo()) {
			case inteiro:
				coluna = new ColunaInt(prop, descricao);
				break;
			case string:
				coluna = new ColunaString(prop, descricao);
				break;
		}
		
		return coluna;
	}

	@Override
	public void fromByteArray(byte[] dados) {
		// TODO Auto-generated method stub
		
	}

}
