package gerenciador;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.buffer.Memoria;
import gerenciador.buffer.interfaces.IMemoryEvents;
import gerenciador.loger.Log;

public class GerenciadorBuffer {
	
	private Memoria memoria;
	private int[] controle;
	private int contador;
	private int hit;
	private int miss;
	private ILog log;
	private GerenciadorArquivos ga;//cache
	
	public GerenciadorBuffer(){
		construtor();
	}
	
	private void construtor(){
		this.memoria = new Memoria(getMemoryEvents());
		controle = new int[Memoria.MEMORY_SIZE_IN_BLOCKS];
		startControlador();
		hit = 0;
		miss = 0;
		log = new Log();
	}
	private IMemoryEvents getMemoryEvents(){
		return new IMemoryEvents() {
			
			@Override
			public Descritor requisitarDescritor(int containerId) {
				
				GerenciadorArquivos ga = getGAFromCache();
				return ga.getBlocoControle((byte) containerId)
						.getDescritor();
			}
		};
	}
	public GerenciadorBuffer(ILog log){
		construtor();
		this.log = log;
	}
	
	/* pedir bloco
	 * verificar se está na memória
	 * 	caso esteja
	 * 		incrementar hit
	 * 		atualizar controle pondo posMem no inicio do controle
	 * 		retornar bloco na posMem
	 * 	caso não esteja
	 * 		incrementar nhit
	 * 		pegar novo bloco no disco
	 * 		verificar se tem espaço na memória
	 * 		 caso tenha
	 * 			incluir onde está vazio
	 * 			atualizar controle pondo a nova posMem no inicio do controle
	 * 		 caso não tenha
	 * 			substituir o bloco usado mais atigamente, na PosMem mais antiga, pelo novo
	 * 			atualizar controle ponto a posMem do novo bloco no inicio
	 * 
	 * */
	
	public int getBloco(RowId tid){
		int posMem = memoria.getPosition(tid);
		
		if(posMem >= 0){
			hit++;
			AtualizarControle(posMem);
			return memoria.getBloco(posMem).getBlocoId();
		}
		miss++;
		Bloco novoBloco = getFromDisk(tid);
				
		posMem = memoria.getPosVazia();
		if(posMem >= 0){
			
			memoria.putBloco(novoBloco, posMem);
			AtualizarControle(posMem);
			
			return novoBloco.getBlocoId();
		}
		
		posMem = controle[Memoria.MEMORY_SIZE_IN_BLOCKS - 1];
		memoria.putBloco(novoBloco, posMem);
		AtualizarControle(posMem);
		
		
		return novoBloco.getBlocoId();
	}
	public Bloco getFromDisk(RowId tid){
		GerenciadorArquivos ga = getGAFromCache();
		return ga.getBloco(tid);
	}
	
	public GerenciadorArquivos getGAFromCache(){
		if(ga == null){
			ga = new GerenciadorArquivos();
		}
			
		return ga;
	}
	
	private void startControlador(){
		for(int i = 0; i < controle.length; i++){
			controle[i] = -1;
		}
	}
	
	
	private void AtualizarControle(int posMem) {
		int index = -1;
		for(int i = 0; i < controle.length; i++){
			if(controle[i] == posMem){
				index = i;
			}
		}
		if(index == -1) index = Memoria.MEMORY_SIZE_IN_BLOCKS - 1;
		
		for(int j = index; j > 0; j-- ){
			controle[j] = controle[j-1];
		}
		controle[0] = posMem;
		
	}

	private void remover(RowId tupleId){
		throw new RuntimeException("Não implementado");
	}
	
	public void resetHitNhit(){
		hit = 0;
		miss = 0;
	}
	public int getHit(){
		return hit;
	}
	public int getMiss(){
		return miss;
	}
	public int getAcessos(){
		return hit+miss;
	}
}
