package gerenciador;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.interfaces.IArquivo;
import gerenciador.arquivos.interfaces.IArquivoEvents;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.buffer.Memoria;
import gerenciador.buffer.interfaces.IMemoryEvents;
import gerenciador.indice.blocos.Node;
import gerenciador.loger.Log;

public class GerenciadorBuffer {

	private Memoria memoria;
	private int[] controle;
	private int contador;
	private int hit;
	private int miss;
	private int swaps;
	private ILog Log;
	private GerenciadorArquivos ga;// cache
	private IArquivoEvents eventosDisco;

	public GerenciadorBuffer() {
		construtor();
	}

	private void construtor() {
		this.memoria = new Memoria(getMemoryEvents());
		controle = new int[Memoria.MEMORY_SIZE_IN_BLOCKS];
		startControlador();
		hit = 0;
		miss = 0;
		swaps = 0;
		Log = new Log();
		GerenciadorArquivos ga = getGAFromCache();
	}

	private IMemoryEvents getMemoryEvents() {
		return new IMemoryEvents() {

			public Descritor requisitarDescritor(int containerId) {

				GerenciadorArquivos ga = getGAFromCache();
				return ga.getBlocoControle((byte) containerId).getDescritor();
			}
		};
	}

	public GerenciadorBuffer(ILog log) {
		construtor();
		this.Log = log;
	}

	/*
	 * pedir bloco verificar se está na memória caso esteja incrementar hit
	 * atualizar controle pondo posMem no inicio do controle retornar bloco na
	 * posMem caso não esteja incrementar nhit pegar novo bloco no disco
	 * verificar se tem espaço na memória caso tenha incluir onde está vazio
	 * atualizar controle pondo a nova posMem no inicio do controle caso não
	 * tenha substituir o bloco usado mais atigamente, na PosMem mais antiga,
	 * pelo novo atualizar controle ponto a posMem do novo bloco no inicio
	 * 
	 */

	public IBloco getBloco(RowId tid) {
		int posMem = memoria.getPosition(tid);
		Log.Write("Buffer => Bloco: " + tid.getBlocoId());
		Log.Write(System.lineSeparator());
		if (posMem >= 0) {

			Log.Write("Buffer => em memória");
			Log.Write(System.lineSeparator());

			hit++;
			atualizarControleLRU(posMem);
			return memoria.getBloco(posMem);
		}

		Log.Write("Buffer => buscar no disco");
		Log.Write(System.lineSeparator());
		miss++;
		IBloco novoBloco = getFromDisk(tid);

		posMem = memoria.getPosVazia();
		if (posMem >= 0) {

			Log.Write("Buffer => adicionado em posição vazia");
			Log.Write(System.lineSeparator());

			memoria.putBloco(novoBloco, posMem);
			atualizarControleLRU(posMem);
			return novoBloco;
		}
		swaps++;
		Log.Write("Buffer => swap");
		Log.Write(System.lineSeparator());

		posMem = controle[Memoria.MEMORY_SIZE_IN_BLOCKS - 1];
		
		WriteDisk(memoria.getBloco(posMem));
		
		memoria.putBloco(novoBloco, posMem);
		atualizarControleLRU(posMem);

		return novoBloco;
	}

	public IBloco getFromDisk(RowId tid) {
		return eventosDisco.RequisitarBloco(tid);
	}

	public void criarBloco(IBloco b) {
		eventosDisco.BlocoAdicionado(b);
	}

	public void WriteDisk(IBloco bloco) {
		eventosDisco.BlocoAlterado(bloco);
	}

	

	public GerenciadorArquivos getGAFromCache() {
		if (ga == null) {
			ga = new GerenciadorArquivos(new ILog() {

				public void Write(String msg) {

				}

				public void Erro(String msg) {

				}
			});
			eventosDisco = ga.getArquivoEvents();
		}

		return ga;
	}

	private void startControlador() {
		for (int i = 0; i < controle.length; i++) {
			controle[i] = -1;
		}
	}

	private void atualizarControleLRU(int posMem) {
		int index = -1;
		for (int i = 0; i < controle.length; i++) {
			if (controle[i] == posMem) {
				index = i;
				break;
			}
		}
		if (index == -1)
			index = Memoria.MEMORY_SIZE_IN_BLOCKS - 1;

		for (int j = index; j > 0; j--) {
			controle[j] = controle[j - 1];
		}
		controle[0] = posMem;

	}

	public void resetHitMissSwaps() {
		hit = 0;
		miss = 0;
		swaps = 0;
	}

	public int getHit() {
		return hit;
	}

	public int getMiss() {
		return miss;
	}

	public double getAcessos() {
		return hit + miss;
	}

	public double getSwaps() {
		return swaps;
	}

	public int getMemoriaSize() {
		return memoria.getSize();
	}

	public void addBloco(IArquivo indice, Node node) {
		indice.addBloco(node);
		getBloco(node.getBlocoTupleId());
	}
}
