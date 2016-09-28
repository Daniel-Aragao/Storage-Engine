package gerenciador.arquivos.blocos;

import java.util.ArrayList;

import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class DadosBloco  implements IBinarizable<DadosBloco>{
	
	private ArrayList<Tupla> tuplas;
	private Descritor descritor;
	
	public DadosBloco(Descritor descritor) {
		this.descritor = descritor;
		setTuplas(new ArrayList<Tupla>());
	}

	public DadosBloco(byte[] dados, Descritor descritor) throws IncorrectFormatException{
		this.descritor = descritor;
		setTuplas(new ArrayList<Tupla>());
		fromByteArray(dados);
	}
	
	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] retorno = new byte[0];
		
		for(Tupla t : tuplas){
			retorno = ByteArrayTools.concatArrays(retorno, t.getByteArray());
		}
		
		return retorno;
	}

	private ArrayList<Tupla> getTuplas() {
		return tuplas;
	}
	
	public Tupla getTupla(int index){
		return tuplas.get(index);
	}
	
	public boolean contains(Tupla tupla){
		return tuplas.contains(tupla);
	}
	
	public boolean contains(int index){
		return tuplas.contains(index);
	}
	
	public void RemoveTupla(Tupla tupla){
		tuplas.remove(tupla);
	}
	
	public int RemoveTupla(int index){
		Tupla tupla = tuplas.remove(index);
		return tupla.getSize();
	}
	
	public boolean isEmpty(){
		return tuplas.isEmpty();
	}
	
	public void addTupla(Tupla tupla){
		tuplas.add(tupla);
	}

	private void setTuplas(ArrayList<Tupla> tuplas) {
		this.tuplas = tuplas;
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		int pointer = 0;
		while(pointer != -1){
			
			int tuplaSize = ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, pointer, 4));
			byte[] tuplaBA = ByteArrayTools.subArray(dados, pointer, tuplaSize);
			
			Tupla tupla = new Tupla(tuplaBA, this.descritor);
						
			this.tuplas.add(tupla);
			
			pointer += tuplaSize;
			if(pointer + 4 >= dados.length || 
					ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, pointer, 4)) == 0)
				pointer = -1;
		}
	}

	public int getOffset(Tupla tupla) {
		
//		for(int i = 0; i < tuplas.size(); i++){
//			if(tuplas.get(i)ge)
//		}
		
		throw new RuntimeException("Não implementado");
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
