package gerenciador.arquivos;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.enums.ETipoBloco;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IArquivoEvents;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.arquivos.interfaces.IBlocoEvents;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.Log;
import gerenciador.utils.ByteArrayTools;

public class Arquivo implements IBinarizable<Arquivo>{
	private BlocoControle blocoControle;
	private ArrayList<Bloco> blocos;
	private IBlocoEvents blocoEvents;
	private IArquivoEvents events;
	private File file;
	
	public Arquivo(BlocoControle blocoControle, File file){		
		this.blocoControle = blocoControle;
		this.file = file;
		blocos = new ArrayList<Bloco>();		
		createEvents();
	}
	
	
	public Arquivo(byte[] dados, File file) throws IncorrectFormatException {
		this.file = file;
		blocos = new ArrayList<Bloco>();
		createEvents();
		fromByteArray(dados);
	}
	
	public Descritor getDescritor(){
		return this.blocoControle.getDescritor();
	}
	
	public File getFile() {
		return file;
	}
	
	public void setArquivoEvent(IArquivoEvents arquivoEvents){
		this.events = arquivoEvents;
	}
	
	private void createEvents(){
		Arquivo a = this;
		blocoEvents = new IBlocoEvents() {
			
			@Override
			public void blocoVazio(Bloco bloco) {
				Log.Write("Bloco vazio");
				removerBloco(bloco);				
			}
			
			@Override
			public void blocoCheio(Tupla tupla) {
				Log.Write("Bloco cheio");
				Bloco novo = criarBloco();
				novo.addTupla(tupla);				
			}

			@Override
			public void blocoAlterado(Bloco bloco) {
				if(events != null){
					events.BlocoAlterado(a, bloco);
				}				
			}
		};
	}
	
	private Bloco criarBloco(){
		Log.Write("Criar bloco");
		Bloco novo = new Bloco(blocoControle.getHeader().getContainerId(), 
				blocoControle.getHeader().getProxBlocoLivre(), 
				ETipoBloco.dados, 
				blocoControle.getDescritor());
		
		addBloco(novo);
		return novo;
	}
	
	public void addBloco(Bloco bloco){
		Log.Write("Adicionar bloco");
		
		bloco.setEvents(blocoEvents);
		this.blocos.add(bloco);
		this.blocoControle.getHeader().incProxBlocoLivre();
		
		if(events != null){
			events.BlocoAdicionado(this, bloco);
		}
	}
	
	public void removerBloco(Bloco bloco){
		Log.Write("Remover bloco");
		
		this.blocoControle.getHeader().decProxBlocoLivre();
		this.blocos.remove(bloco);
		
		if(events != null){
			events.BlocoRemovido(this, bloco);
		}
	}
	
	public void AdicionarLinha(Tupla tupla){
		Log.Write("Adicionar linha");
		
		Bloco bloco = requisitarBloco(getBlocoControle().getProxBlocoLivre() - 1);
		
		bloco.addTupla(tupla);		
	}
	
	public void RemoverLinha(Tupla tupla){
		Log.Write("Remover linha");
		
		if(this.blocoControle.getProxBlocoLivre() == 1){
			throw new RuntimeException("Não existem blocos para efetur a remoção");			
		}
		
		Bloco bloco = requisitarBloco(getBlocoControle().getProxBlocoLivre() - 1);
		bloco.removeTupla(tupla);
//		throw new RuntimeException("Não implementado");
	}
	
//	private Bloco requisitarBloco(){
//		
//		int requisitoId = this.blocoControle.getHeader().getProxBlocoLivre() - 1;
//		Log.Write("Requisitar bloco..."+requisitoId);
//		
//		if(requisitoId == 0) return criarBloco();
//		
//		Optional<Bloco> opt = blocos.stream().findAny().filter(b -> b.getBlocoId() == requisitoId);
//		
//		if(opt.isPresent()) return opt.get();
//		
//		Bloco retorno = this.events.RequisitarBloco(this, requisitoId);
//		retorno.setEvents(blocoEvents);
//		blocos.add(retorno);
//		
//		return  retorno;
//	}
	
	private ILog Log;
	private Bloco requisitarBloco(int requisitoId){
		this.Log = new Log();

		if(requisitoId == 0) return criarBloco();
		
		if(requisitoId > this.blocoControle.getHeader().getProxBlocoLivre() - 1){
			throw new RuntimeException("requisitoId deve ser "
					+ "igual ou menor que o proximo bloco id");
		}
		
		Log.Write("Requisitar bloco..."+requisitoId);
				
		Optional<Bloco> opt = blocos.stream().findAny().filter(b -> b.getBlocoId() == requisitoId);
		
		if(opt.isPresent()) return opt.get();
		
		Bloco retorno = this.events.RequisitarBloco(this, requisitoId);
		retorno.setEvents(blocoEvents);
		blocos.add(retorno);
		
		return  retorno;
	}
	
	public BlocoControle getBlocoControle() {
		return blocoControle;
	}
	
	public byte getId(){
		return blocoControle.getHeader().getContainerId();
	}


	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		if(blocos.size() != blocoControle.getHeader().getProxBlocoLivre() - 1){
			throw new IncorrectFormatException("Devem ser carregados todos os blocos "
					+ "do arquivo para chamar o método getByteArray()");
		}
			
			
		byte[] blocosArray = blocoControle.getByteArray();
		for(Bloco bloco : blocos){
			if(!bloco.getDados().isEmpty()){
				blocosArray = ByteArrayTools.concatArrays(blocosArray, bloco.getByteArray());				
			}
		}
		
		return blocosArray;
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");
		
	}
	
	@Override
	public String toString() {
		String retorno = "";
		
		for(int i = 1; i < getBlocoControle().getProxBlocoLivre(); i++){
			requisitarBloco(i);
		}
		
		for(int i = 0 ; i < blocos.size(); i++){
			retorno += blocos.get(i).toString() + "\n";
		}
		return retorno;
//		throw new RuntimeException("Não implementado");// sugestão, requisitar vai receber id
	}
}
