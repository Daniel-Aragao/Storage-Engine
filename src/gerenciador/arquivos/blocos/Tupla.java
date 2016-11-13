package gerenciador.arquivos.blocos;

import java.util.ArrayList;

import gerenciador.RowId;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.ITupla;
import gerenciador.utils.ByteArrayTools;

public class Tupla implements ITupla{

	private int size;	
	private ArrayList<Coluna> colunas;
	private Descritor descritor;
	private RowId tupleId;
	
	
	public Tupla(String[] props, RowId tupleId, Descritor descritor)throws IncorrectFormatException{
		setColunas(new ArrayList<>());
		this.size = 0;
		this.tupleId = tupleId;
		
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
	
	public Tupla(Coluna[] props, RowId tupleId, Descritor descritor)throws IncorrectFormatException{
		setColunas(new ArrayList<>());
		this.size = 0;
		this.tupleId = tupleId;
		
		if (props.length != descritor.getNumberOfColumns()){
			throw new IncorrectFormatException("Número de colunas inseridas é diferente"
					+ "do número de colunas da tabela");
		}
		
		for(int i = 0; i < props.length; i++){			
			colunas.add(props[i]);
			this.size += props[i].getColumnSize();
		}		
		
		this.size += 4;
	}
	
	public Tupla(byte[] dados, RowId tupleId, Descritor descritor) throws IncorrectFormatException{		
		this.descritor = descritor;
		this.tupleId = tupleId;
		setColunas(new ArrayList<Coluna>());
		fromByteArray(dados);
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.ITupla#getTupleId()
	 */
	@Override
	public RowId getTupleId() {
		return tupleId;
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.ITupla#setTupleId(gerenciador.RowId)
	 */
	@Override
	public void setTupleId(RowId tupleId) {
		this.tupleId = tupleId;
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] retorno = ByteArrayTools.intToByteArray(size);
		
		for(Coluna c : colunas){
			retorno = ByteArrayTools.concatArrays(retorno, c.getByteArray());
		}
		
		return retorno;
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.ITupla#getSize()
	 */
	@Override
	public int getSize() {
		return size;
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.ITupla#getColunas()
	 */
	@Override
	public ArrayList<Coluna> getColunas() {
		return colunas;
	}

	private void setColunas(ArrayList<Coluna> colunas) {
		this.colunas = colunas;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.ITupla#getColuna(int)
	 */
	@Override
	public Coluna getColuna(int index){
		return this.colunas.get(index);
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.ITupla#createColuna(java.lang.String, gerenciador.arquivos.blocosControle.UnidadeDescricao)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.ITupla#createColuna(byte[], gerenciador.arquivos.blocosControle.UnidadeDescricao)
	 */
	@Override
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
		// BA = Byte Array
		if(dados.length < (4 + 6 + 4)) 
				throw new IncorrectFormatException("Tupla não tem o mínimo de "+14+" bytes exigido");
		
		this.size = ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 4));
		
		int pointer = 4, cont = 0;
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
	
	@Override
	public String toString() {
		String retorno = "";
		for(Coluna c : colunas){
			retorno += c.toString() + " ";
		}
		return retorno;
	}
//BA =  Byte Array
}





















