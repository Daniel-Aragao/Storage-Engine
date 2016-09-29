package gerenciador.buffer;

import gerenciador.arquivos.blocos.Bloco;

public class Memoria {
	public static final int MEMORY_SIZE = 10;
	private Bloco[] blocos;
	
	public Memoria(){
		blocos = new Bloco[MEMORY_SIZE];
	}
	
	public boolean contains(Bloco bloco){
		return contains(bloco.getBlocoId());
	}
	
	public boolean contains(int blocoId){
		
		for(Bloco b : blocos){
			if(blocoId == b.getBlocoId()){
				return true;
			}
		}
		return false;
	}

	public Bloco getBloco(int blocoId) {
		throw new RuntimeException("Não implementado");
		
	}
}
