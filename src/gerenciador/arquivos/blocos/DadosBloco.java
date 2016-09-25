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
	
	public Tupla getTupla(int index){
		return tuplas.get(index);
	}
	
	public void addTupla(byte[] dados) throws IncorrectFormatException{
		tuplas.add(new Tupla(dados, descritor));
	}
	
	public void addTupla(Tupla tupla){
		tuplas.add(tupla);
	}

	public void setTuplas(ArrayList<Tupla> tuplas) {
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
			if(pointer >= dados.length || 
					ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, pointer, 4)) == 0)
				pointer = -1;
		}
	}
	
}
