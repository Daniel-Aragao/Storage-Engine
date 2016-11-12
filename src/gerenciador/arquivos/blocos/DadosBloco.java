package gerenciador.arquivos.blocos;

import java.util.ArrayList;

import gerenciador.RowId;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.arquivos.interfaces.ITupla;
import gerenciador.utils.ByteArrayTools;

public class DadosBloco  implements IBinarizable<DadosBloco>{
	
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
	
	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] retorno = new byte[0];
		
		for(ITupla t : tuplas){
			retorno = ByteArrayTools.concatArrays(retorno, t.getByteArray());
		}
		
		return retorno;
	}
	
	public ITupla getTupla(int index){
		return tuplas.get(index);
	}
	public int getSize(){
		return tuplas.size();
	}
	
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
	
	public boolean contains(ITupla tupla){
		return tuplas.contains(tupla);
	}
	
	public boolean contains(int index){
		return tuplas.contains(index);
	}
	
	public void RemoveTupla(ITupla tupla){
		tuplas.remove(tupla);
	}
	
	public int RemoveTupla(int index){
		ITupla tupla = tuplas.remove(index);
		return tupla.getSize();
	}
	
	public boolean isEmpty(){
		return tuplas.isEmpty();
	}
	
	public void addTupla(ITupla tupla){
		tuplas.add(tupla);
	}

	private void setTuplas(ArrayList<ITupla> tuplas) {
		this.tuplas = tuplas;
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		int pointer = 0;
		while(pointer != -1){
			
			int tuplaSize = ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, pointer, 4));
			byte[] tuplaBA = ByteArrayTools.subArray(dados, pointer, tuplaSize);
			
			RowId tupleId = new RowId(this.tupleBlocoId.getContainerId(), 
					this.tupleBlocoId.getBlocoId(), 
					pointer + Bloco.HEADER_BLOCO_SIZE);
			Tupla tupla = new Tupla(tuplaBA, tupleId, this.descritor);
						
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
		return super.toString();
	}

	public int size() {
		return tuplas.size();
	}
}
