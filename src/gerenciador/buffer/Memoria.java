package gerenciador.buffer;

import gerenciador.RowId;
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
	
	public boolean contains(RowId tid){
		
		for(Bloco b : blocos){
			if(tid.isSameBloco(b.getBlocoTupleId())){
				return true;
			}
		}
		return false;
	}

	public Bloco getBloco(RowId tid) {
		for(int i = 0; i < blocos.length ; i++){
			if(tid.isSameBloco(blocos[i].getBlocoTupleId())){
				return blocos[i];
			}
		}
		return null;
	}
	public Bloco getBloco(int index){
		return blocos[index];
	}	
	
	public int getPosition(RowId tid){
		for(int i = 0; i < blocos.length ; i++){
			if(tid.isSameBloco(blocos[i].getBlocoTupleId())){
				return i;
			}
		}
		return -1;
	}
	
	public void putBloco(Bloco b, int posMem){
		blocos[posMem] = b;
		throw new RuntimeException("Não implementado");
	}
	
	public int getPosVazia(){
		for(int i = 0; i < blocos.length; i++){
			if(blocos[i] == null) return i;
		}
		return -1;
	}

	public Bloco remover(RowId tid) {
		int i = getPosition(tid);
		if(i < 0) return null;
		
		Bloco removido = blocos[i];
		
		for(; i < blocos.length - 1; i++){
			blocos[i] = blocos[i+1];
		}
		throw new RuntimeException("Não implementado");
//		contador--;
//		return removido;
	}

	private void opa() {
		throw new RuntimeException("Não implementado");
		
	}
}
