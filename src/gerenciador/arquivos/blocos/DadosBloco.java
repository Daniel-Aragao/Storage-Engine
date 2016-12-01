package gerenciador.arquivos.blocos;

import java.util.ArrayList;

import gerenciador.RowId;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.ITupla;
import gerenciador.utils.ByteArrayTools;

public class DadosBloco implements IDados{
	
	private ArrayList<ITupla> tuplas;
	private Descritor descritor;
	private RowId tupleBlocoId ;
	
	public DadosBloco(Descritor descritor) {
		this.descritor = descritor;
		setTuplas(new ArrayList<ITupla>());
	}

	public DadosBloco(byte[] dados,RowId tupleBlocoId, Descritor descritor) throws IncorrectFormatException{
		this.descritor = descritor;
		this.tupleBlocoId = tupleBlocoId; 
		setTuplas(new ArrayList<ITupla>());
		fromByteArray(dados);
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#getByteArray()
	 */
	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] retorno = new byte[0];
		
		for(ITupla t : tuplas){
			retorno = ByteArrayTools.concatArrays(retorno, t.getByteArray());
		}
		
		return retorno;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#getTupla(int)
	 */
	@Override
	public ITupla getTupla(int index){
		return tuplas.get(index);
	}
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#getSize()
	 */
	@Override
	public int getSize(){
		return tuplas.size();
	}
	
	public ArrayList<ITupla> getTuplas(){
		return this.tuplas;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#getOffSet(int)
	 */
	@Override
	public int getOffSet(int i){
		int retorno = Bloco.HEADER_BLOCO_SIZE;
		ITupla tuple = tuplas.get(i);
		
		for(ITupla t : tuplas){
			if(tuple == t){
				return retorno;
			}
			retorno += t.getSize();
		}
		
		return -1;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#contains(gerenciador.arquivos.interfaces.ITupla)
	 */
	@Override
	public boolean contains(ITupla tupla){
		return tuplas.contains(tupla);
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#contains(int)
	 */
	@Override
	public boolean contains(int index){
		return tuplas.contains(index);
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#RemoveTupla(gerenciador.arquivos.interfaces.ITupla)
	 */
	@Override
	public void RemoveTupla(ITupla tupla){
		tuplas.remove(tupla);
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#RemoveTupla(int)
	 */
	@Override
	public int RemoveTupla(int index){
		ITupla tupla = tuplas.remove(index);
		return tupla.getSize();
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#isEmpty()
	 */
	@Override
	public boolean isEmpty(){
		return tuplas.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#addTupla(gerenciador.arquivos.interfaces.ITupla)
	 */
	@Override
	public void addTupla(ITupla tupla){
		tuplas.add(tupla);
	}

	private void setTuplas(ArrayList<ITupla> tuplas) {
		this.tuplas = tuplas;
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#fromByteArray(byte[])
	 */
	
	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		int pointer = 0;
		while(pointer != -1){
			
			int tuplaSize = ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, pointer, 4));
			byte[] tuplaBA = ByteArrayTools.subArray(dados, pointer, tuplaSize);
			
			RowId tupleId = new RowId(this.tupleBlocoId.getContainerId(), 
					this.tupleBlocoId.getBlocoId(), 
					pointer + Bloco.HEADER_BLOCO_SIZE);
			ITupla tupla = new Tupla(tuplaBA, tupleId, this.descritor);
						
			this.tuplas.add(tupla);
			
			pointer += tuplaSize;
			if(pointer + 4 >= dados.length || 
					ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, pointer, 4)) == 0)
				pointer = -1;
		}
	}

	
	@Override
	public String toString() {
		String retorno = "";
		for(int i = 0;i < tuplas.size(); i++){
			retorno += "" + tuplas.toString() +"\n";			
		}
		return retorno.toString();
	}

	/* (non-Javadoc)
	 * @see gerenciador.arquivos.blocos.IDados#size()
	 */
	@Override
	public int size() {
		return tuplas.size();
	}
}
