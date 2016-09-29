package gerenciador.buffer;

import gerenciador.TupleId;
import gerenciador.arquivos.blocos.Bloco;

public class Memoria {
	public static final int MEMORY_SIZE = 10;
	private Bloco[] blocos;
	
	public Memoria(){
		blocos = new Bloco[MEMORY_SIZE];
	}
	
	public boolean contains(Bloco bloco){
		throw new RuntimeException("Não implementado");
	}
	
	public boolean contains(TupleId tid){
		
		for(Bloco b : blocos){
			if(tid.isSameBloco(b.getBlocoTupleId())){
				return true;
			}
		}
		return false;
	}

	public Bloco getBloco(int blocoId) {
		throw new RuntimeException("Não implementado");
		
	}
}
