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
	private Descritor descritor;
	
	
	public Tupla(String[] props, Descritor descritor)throws IncorrectFormatException{
		setColunas(new ArrayList<>());
		this.size = 0;
		
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
		
		this.size += 4;
	}
	
	public Tupla(byte[] dados, Descritor descritor) throws IncorrectFormatException{		
		this.descritor = descritor;
		setColunas(new ArrayList<>());
		fromByteArray(dados);
	}
	
	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
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
	
	public Coluna getColuna(int index){
		return this.colunas.get(index);
	}
	
	public Coluna createColuna(String prop, UnidadeDescricao descricao) throws IncorrectFormatException{
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
	
	public Coluna createColuna(byte[] ba, UnidadeDescricao descricao) throws IncorrectFormatException{
		Coluna coluna = null;
		
		switch (descricao.getTipo()) {
			case inteiro:
				coluna = new ColunaInt(ba);
				break;
			case string:
				coluna = new ColunaString(ba);
				break;
		}
		
		return coluna;
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		if(dados.length < (4 + 6 + 4)) 
				throw new IncorrectFormatException("Tupla não tem o mínimo de "+14+" bytes exigido");
		
		this.size = ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 4));
		
		int pointer = 4, cont = 0;;
		while(pointer != -1){
			
			short colSize = (short) ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, pointer, 2));
			byte[] colBA = ByteArrayTools.subArray(dados, pointer, colSize);
			
			Coluna col =  createColuna(colBA, this.descritor.getUnidadeDescricao(cont));
						
			this.colunas.add(col);
			
			cont++;
			pointer += colSize;
			if(pointer >= dados.length)
				pointer = -1;
		}
		
	}
//BA =  Byte Array
}





















