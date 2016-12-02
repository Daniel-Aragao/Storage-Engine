package gerenciador.arquivos;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IArquivo;
import gerenciador.arquivos.interfaces.IArquivoEvents;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.IBlocoEvents;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.arquivos.interfaces.ITupla;
import gerenciador.loger.Log;
import gerenciador.utils.ByteArrayTools;

public class Arquivo implements IArquivo{
	private BlocoControle blocoControle;
	private ArrayList<IBloco> blocos;
	private IBlocoEvents blocoEvents;
	private IArquivoEvents events;
	private File file;
	
	private ILog Log;
	
	public Arquivo(BlocoControle blocoControle, File file){		
		this.Log = new Log();
		this.blocoControle = blocoControle;
		this.file = file;
		blocos = new ArrayList<IBloco>();		
		createEvents();
	}
	
	
	public Arquivo(byte[] dados, File file) throws IncorrectFormatException {
		this.Log = new Log();
		this.file = file;
		blocos = new ArrayList<IBloco>();
		createEvents();
		fromByteArray(dados);
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.IArquivo#getDescritor()
	 */
	
	public Descritor getDescritor(){
		return this.blocoControle.getDescritor();
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.IArquivo#getFile()
	 */
	
	public File getFile() {
		return file;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.IArquivo#setArquivoEvent(gerenciador.arquivos.interfaces.IArquivoEvents)
	 */
	
	public void setArquivoEvent(IArquivoEvents arquivoEvents){
		this.events = arquivoEvents;
	}
	
	private void createEvents(){
		IArquivo a = this;
		blocoEvents = new IBlocoEvents() {
			
			
			public void blocoVazio(IBloco bloco) {
				Log.Write("Bloco vazio");
				removerBloco(bloco);				
			}
			
			
			public void blocoCheio(ITupla tupla) {
				Log.Write("Bloco cheio");
				IBloco novo = criarBloco();
				novo.addTupla(tupla);				
			}

			
			public void blocoAlterado(IBloco bloco) {
				if(events != null){
					events.BlocoAlterado(a, bloco);
				}				
			}
		};
	}
	
	private IBloco criarBloco(){
		Log.Write("Criar bloco");
		IBloco novo = null;
		if (blocoControle.getHeader().getTipo() == ETipoBlocoArquivo.dados){
			try {
				novo = new Bloco(blocoControle.getHeader().getContainerId(), 
						blocoControle.getHeader().getProxBlocoLivre(), 
						ETipoBlocoArquivo.dados, 
						blocoControle.getDescritor());
			} catch (IncorrectFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}else{
			throw new RuntimeException("Não implementado");
//			try {
//				novo = new Node();
//			} catch (IncorrectFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}		
		}
		
		addBloco(novo);
		return novo;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.IArquivo#addBloco(gerenciador.arquivos.blocos.Bloco)
	 */
	
	public void addBloco(IBloco bloco){
		Log.Write("Adicionar bloco");
		
		bloco.setEvents(blocoEvents);
		this.blocos.add(bloco);
		this.blocoControle.getHeader().incProxBlocoLivre();
		
		if(events != null){
			events.BlocoAdicionado(this, bloco);
		}
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.IArquivo#removerBloco(gerenciador.arquivos.blocos.Bloco)
	 */
	
	public void removerBloco(IBloco bloco){
		Log.Write("Remover bloco");
		
		this.blocoControle.getHeader().decProxBlocoLivre();
		this.blocos.remove(bloco);
		
		if(events != null){
			events.BlocoRemovido(this, bloco);
		}
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.IArquivo#AdicionarLinha(gerenciador.arquivos.blocos.Tupla)
	 */
	
	public void AdicionarLinha(ITupla tupla){
		Log.Write("Adicionar linha");
		
		IBloco bloco = requisitarBloco(getBlocoControle().getProxBlocoLivre() - 1);
		
		bloco.addTupla(tupla);		
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.IArquivo#RemoverLinha(gerenciador.arquivos.blocos.Tupla)
	 */
	
	public void RemoverLinha(ITupla tupla){
		Log.Write("Remover linha");
		
		if(this.blocoControle.getProxBlocoLivre() == 1){
			throw new RuntimeException("Não existem blocos para efetur a remoção");			
		}
		
		IBloco bloco = requisitarBloco(getBlocoControle().getProxBlocoLivre() - 1);
		bloco.removeTupla(tupla);
	}
	
	public IBloco requisitarBloco(int requisitoId){

		if(requisitoId == 0) return criarBloco();
		
		if(requisitoId > this.blocoControle.getHeader().getProxBlocoLivre() - 1){
			throw new RuntimeException("o bloco requisitado "
					+ "não pertence a este arquivo");
		}
		
		Log.Write("Requisitar bloco..."+requisitoId);
				
		Optional<IBloco> opt = blocos.stream().findAny().filter(b -> b.getBlocoId() == requisitoId);
		
		if(opt.isPresent()) return opt.get();
		
		IBloco retorno = this.events.RequisitarBloco(this, requisitoId);
		retorno.setEvents(blocoEvents);
		blocos.add(retorno);
		
		return  retorno;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.IArquivo#getBlocoControle()
	 */
	
	public BlocoControle getBlocoControle() {
		return blocoControle;
	}
	
	/* (non-Javadoc)
	 * @see gerenciador.arquivos.IArquivo#getId()
	 */
	
	public byte getId(){
		return blocoControle.getHeader().getContainerId();
	}

	
	
	
	public byte[] getByteArray() throws IncorrectFormatException {
		if(blocos.size() != blocoControle.getHeader().getProxBlocoLivre() - 1){
			throw new IncorrectFormatException("Devem ser carregados todos os blocos "
					+ "do arquivo para chamar o método getByteArray()");
		}
			
			
		byte[] blocosArray = blocoControle.getByteArray();
		for(IBloco bloco : blocos){
			if(!bloco.getDados().isEmpty()){
				blocosArray = ByteArrayTools.concatArrays(blocosArray, bloco.getByteArray());				
			}
		}
		
		return blocosArray;
	}

	
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");
		
	}
	
	
	public String toString() {
		String retorno = "";
		
		carregarBlocos();
		
		for(int i = 0 ; i < blocos.size(); i++){
			retorno += blocos.get(i).toString() + "\n";
		}
		return retorno;
	}


	
	public String getNome() {
		return blocoControle.getHeader().getNome();
	}


	
	public ETipoBlocoArquivo getTipo() {
		return this.blocoControle.getHeader().getTipo();
	}
	
	public String adicionarIndice(byte containerId){
		String retorno = this.blocoControle.getHeader().addIndice(containerId);
		
		events.BlocoControleAlterado(this);
		
		return retorno;
	}
	
	public void carregarBlocos(){
		for(int i = 1; i < getBlocoControle().getProxBlocoLivre(); i++){
			requisitarBloco(i);
		}
	}


	public void setQtdIndice(byte id) {
		blocoControle.setQtdeIndice(id);
		events.BlocoControleAlterado(this);
	}


	
	public boolean hasIndice(byte id) {
		if(! (this.blocoControle.getHeader().qtdIndices() > 0)){
			return false;
		}else{
			
		}
		
		return true;
	}
	
	
	public int qtdIndices(){
		return blocoControle.getHeader().qtdIndices();
	}
	
}
