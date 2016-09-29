package gerenciador;

import gerenciador.buffer.Memoria;

public class GerenciadorBuffer {
	
	private Memoria memoria;
	private int[] controle;
	
	public GerenciadorBuffer(){
		this.memoria = new Memoria();
		controle = new int[Memoria.MEMORY_SIZE];
	}
	
	public int getBloco(TupleId tid){
		if(memoria.contains(tid.getBlocoId())){
			memoria.getBloco(tid.getBlocoId());
			return memoria.getBloco(tid.getBlocoId()).getBlocoId();
		}
		
		
		
		
		return 0;
	}
	
	private void hit(int posMemoria){
		
	}
	
}
