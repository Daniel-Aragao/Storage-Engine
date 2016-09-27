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
		blocoEvents = new IBlocoEvents() {
			
			@Override
			public void blocoVazio(Bloco bloco) {
				removerBloco(bloco);				
			}
			
			@Override
			public void blocoCheio(Tupla tupla) {
				Bloco novo = criarBloco();
				novo.addTupla(tupla);				
			}
		};
	}
	
	private Bloco criarBloco(){
		Bloco novo = new Bloco(blocoControle.getHeader().getContainerId(), 
				blocoControle.getHeader().getProxBlocoLivre(), 
				ETipoBloco.dados, 
				blocoControle.getDescritor());
		
		addBloco(novo);
		return novo;
	}
	
	public void addBloco(Bloco bloco){
		bloco.setEvents(blocoEvents);
		this.blocos.add(bloco);
		this.blocoControle.getHeader().incProxBlocoLivre();
		
		if(events != null){
			events.BlocoAdicionado(this, bloco);
		}
	}
	
	public void removerBloco(Bloco bloco){
		this.blocoControle.getHeader().decProxBlocoLivre();
		this.blocos.remove(bloco);
		
		if(events != null){
			events.BlocoRemovido(this, bloco);
		}
	}
	
	public void AdicionarLinha(Tupla tupla){
		Bloco bloco = requisitarBloco();
		
		bloco.addTupla(tupla);
		
		if(events != null){
			events.BlocoAlterado(this, bloco);
		}
	}
	
	public void RemoverLinha(Tupla tupla){
		if(this.blocoControle.getProxBlocoLivre() == 1){
			throw new RuntimeException("N�o existem blocos para efetur a remo��o");			
		}
		
		Bloco bloco = requisitarBloco();
		throw new RuntimeException("N�o implementado");
	}
	
	private Bloco requisitarBloco(){
		int requisitoId = this.blocoControle.getHeader().getProxBlocoLivre() - 1;
		
		if(requisitoId == 0) return criarBloco();
		
		Optional<Bloco> opt = blocos.stream().findAny().filter(b -> b.getBlocoId() == requisitoId);
		
		if(opt.isPresent()) return opt.get();
		
		Bloco retorno = this.events.RequisitarBloco(this, requisitoId);
		retorno.setEvents(blocoEvents);
		blocos.add(retorno);
		
		return  retorno;
	}
	
	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		if(blocos.size() != blocoControle.getHeader().getProxBlocoLivre() - 1){
			throw new IncorrectFormatException("Devem ser carregados todos os blocos "
					+ "do arquivo para chamar o m�todo getByteArray()");
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
		throw new RuntimeException("N�o implementado");
		
	}
}
