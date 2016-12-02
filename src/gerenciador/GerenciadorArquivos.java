package gerenciador;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IArquivo;
import gerenciador.arquivos.interfaces.IArquivoEvents;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.arquivos.interfaces.ITupla;
import gerenciador.indice.blocos.Node;
import gerenciador.loger.Log;
import gerenciador.utils.IO_Operations;

public class GerenciadorArquivos {
	public static final char CARACTERE_STRING = 'A',
							CARACTERE_INTEIRO = 'I';
	public static final String CARACTERE_SEPARADOR = "\\|";
	
	public static final String 
							REGEX_COLUMN = "([^\\[]+)\\[(.*)\\((.*)\\)\\]";
//							CARACTERE_TIPO[]= {'[',']'},
//							CARACTERE_TAMANHO[]= {'(',')'},
	
	
	public static final File DISC_PATH = 
			new File("C:\\Users\\danda_000\\git\\Storage-Engine\\res\\Disco");
//			new File("C:\\Users\\pedro\\Documents\\GitHub\\Storage-Engine\\res\\Disco");
	
	private ILog Log;
	private IArquivoEvents ArquivoEvents;
	private IArquivo arquivoCached;
	
	public GerenciadorArquivos() {
		construct();
	}
	private void construct(){
		this.Log = new Log();
		Log.Write("GerenciadorArquivos iniciado..");
		createArquivoEvents();
	}
	public GerenciadorArquivos(ILog log) {
		construct();
		this.Log = log;
	}
	public void createArquivoEvents(){
		this.ArquivoEvents = new IArquivoEvents() {
			
			
			public void BlocoRemovido(IArquivo a, IBloco b) {
				atualziarBlocoControle(a.getFile(),a.getBlocoControle());				
				Log.Write("Bloco removido");
			}
			@Override
			public void BlocoRemovido(IBloco b) {
				IArquivo a = loadArquivoFromCache(b.getBlocoTupleId().getContainerId());
				atualziarBlocoControle(a.getFile(),a.getBlocoControle());				      		  				atualziarBlocoControle(a.getFile(),a.getBlocoControle());				      
				Log.Write("Bloco removido");				      
			}		  			
			
			public void BlocoAdicionado(IArquivo a, IBloco b) {
				atualziarBlocoControle(a.getFile(), a.getBlocoControle());
				EscreverBloco(a.getFile(), b);				
				Log.Write("Bloco adicionado");
			}
			
			@Override
			public void BlocoAdicionado(IBloco b) {
				IArquivo a = loadArquivoFromCache(b.getBlocoTupleId().getContainerId());
				EscreverBloco(a.getFile(), b);				      
				Log.Write("Bloco adicionado");
			}		  			

			
			public IBloco RequisitarBloco(IArquivo a, int blocoId) {
				Log.Write("Requisitar bloco ao disco");
				return getBloco(a.getFile(), blocoId, a.getDescritor());
			}
			
			@Override
			public IBloco RequisitarBloco(RowId rowid) {
				Log.Write("Requisitar bloco ao disco");
				return getBloco(rowid);
			}

			
			public void BlocoAlterado(IArquivo a, IBloco b) {
				EscreverBloco(a.getFile(), b);				
				Log.Write("Bloco alterado");
			}
			
			@Override
 			public void BlocoAlterado(IBloco b) {
 				EscreverBloco(b);				
 				Log.Write("Bloco alterado");
 				
  			}		  			
			
			
			public void BlocoControleAlterado(IArquivo a) {
				atualziarBlocoControle(a.getFile(),a.getBlocoControle());				
			}
		};
	}
	public IArquivo CriarArquivo(String nome, UnidadeDescricao[] propriedades, ETipoBlocoArquivo tipo){
		Log.Write("- Iniciar criação de arquivo");
		
		Log.Write("Criar diretório");
		
		criarDiretório();
		
		Log.Write("Pegar container id");
		
		byte containerId =  getNextContainerId();
		
		Log.Write("Containerid: "+containerId);
		
		Descritor descritor = null;
		
		descritor = new Descritor(propriedades);		
		
		return processarCriacao(containerId, nome, descritor, tipo);
	}

	public IArquivo CriarArquivo(String nome, String propriedades, ETipoBlocoArquivo tipo)	{
		Log.Write("- Iniciar criação de arquivo");
		
		String [] props = propriedades.split(CARACTERE_SEPARADOR);
		
		Log.Write("Criar diretório");
		
		criarDiretório();
		
		Log.Write("Pegar container id");
		
		byte containerId =  getNextContainerId();
		
		Log.Write("Containerid: "+containerId);
		
		Descritor descritor = null;
		
		try {
			descritor = new Descritor(props);
		} catch (IncorrectFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return processarCriacao(containerId, nome, descritor, tipo);
	}
	
	private IArquivo processarCriacao(byte containerId, String nome, Descritor descritor, ETipoBlocoArquivo tipo){
		File file = null;
		
		try{
			file = generateFile(containerId);
			// certificar que o arquivo vai ser criado
			if(file.createNewFile()){
				Log.Write("Criar bloco de controle");
				BlocoControle blocoControle = new BlocoControle(nome, descritor, containerId, tipo);
				
				Log.Write("Criar Arquivo");
				IArquivo arquivo = new Arquivo(blocoControle, file);
				arquivo.setArquivoEvent(ArquivoEvents);
				
				Log.Write("Gravar Arquivo");
				IO_Operations.writeFile(file, arquivo.getByteArray(), 0);
				
				arquivoCached = arquivo;
				
				Log.Write("\n");
				return arquivo;
			}else{
				Log.Write("Erro ao criar o arquivo");
				throw new RuntimeException("Erro ao criar o arquivo");
			}
		}catch(IncorrectFormatException e){		
			//Erros internos a geração dos blocos
			Log.Erro(e.getMessage());
			Log.Erro("Erro ao gerar o arquivo");
			
			Log.Write("O arquivo não foi criado");
			//Garantir que o arquivo será deletado após algum erro na criação			
			for(int j = 0; j < 10 && !file.delete();j++);
			
			file = null;			
			
		} catch (IOException e) {
			//Erro na criação do arquivo
			Log.Erro("Erro ao criar o arquivo");
			e.printStackTrace();
			file = null;
		}
		return null;
	}

	private void criarDiretório() {
		if(!DISC_PATH.exists()){
			Log.Write("Diretório inexistente");
			Log.Write("Criando diretório...");
			if(!DISC_PATH.mkdirs()){
				Log.Write("Erro ao criar diretório");
				throw new RuntimeException("Erro ao criar diretório");
			}
		}		
	}

	public File generateFile(byte i){
		return new File(GerenciadorArquivos.DISC_PATH.getAbsolutePath()+"\\arquivo-"+i+".txt");
	}		
	
	private byte getNextContainerId(){
		File f = null;
		
		for(byte i = 1; i <= 255 ;i++){
			f = generateFile(i);
			if(!f.exists()){
				return i;
			}
		}
		Log.Erro("DISC_PATH atingiu o máximo de arquivos");
		throw new RuntimeException("DISC_PATH atingiu o máximo de arquivos");		
	}
	
	public void AdicionarLinha(byte containerId, String props){
		Log.Write("-Iniciar adição de linha");
		if(props.isEmpty()){
			Log.Write("Impossível inserir linha sem propriedades");
			return;
		}
		Log.Write("Verificando existência do container/arquivo");
		File file = generateFile(containerId);
		
		if(!file.exists()){
			Log.Erro("Arquivo inexistente");
			throw new RuntimeException("Arquivo inexistente");
		}
		// não esquecer o rowId
		//
		IArquivo arquivo = loadArquivoFromCache(containerId);
		 
		
		ITupla tupla = null;
		try {
			Log.Write("Montando tupla..."+props);
			tupla = new Tupla(props.split(CARACTERE_SEPARADOR),null, arquivo.getDescritor());
			
			arquivo.AdicionarLinha(tupla);
			Log.Write("\n");
		} catch (IncorrectFormatException e) {
			Log.Erro(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	private BlocoControle getBlocoControle(File file){
		Log.Write("get bloco de controle");
		byte[] bloco = IO_Operations
				.readFromFile(file, 0, BlocoControle.TAMANHO_BLOCO);
			
		BlocoControle blocoControle = new BlocoControle(bloco);
		return blocoControle;
	}
	public BlocoControle getBlocoControle(byte containerId){
		return getBlocoControle(generateFile(containerId));
	}
	public IArquivo getArquivo(byte containerId){		
		IArquivo arquivo = loadArquivoFromCache(containerId);
		
		return arquivo;
	}
	
	private IArquivo loadArquivoFromCache(byte containerId){
		IArquivo arquivo = null;
		if(arquivoCached != null && arquivoCached.getId() == containerId){
			Log.Write("Arquivo em cache");
			arquivo = arquivoCached;
		}else{
			Log.Write("Inicializando o arquivo encontrado");
			File file = generateFile(containerId);
			
			arquivo = new Arquivo(getBlocoControle(file), file);
			arquivo.setArquivoEvent(ArquivoEvents);
			
			arquivoCached = arquivo;
		} 
		return arquivo;
	}
	
	private IArquivo loadArquivoFromCache(File file){
		IArquivo arquivo = null;
		if(arquivoCached != null && arquivoCached.getFile().equals(file)){
			Log.Write("Arquivo em cache");
			arquivo = arquivoCached;
		}else{
			Log.Write("Inicializando o arquivo encontrado");
			
			arquivo = new Arquivo(getBlocoControle(file), file);
			arquivo.setArquivoEvent(ArquivoEvents);
			
			arquivoCached = arquivo;
		} 
		return arquivo;
	}
	
	public IBloco getBloco(RowId tid) {
		return getBloco(tid.getContainerId(), tid.getBlocoId());
	}
	
	public IBloco getBloco(byte containerId, int blocoId){
		IArquivo arquivo = loadArquivoFromCache(containerId);
		
		return getBloco(arquivo.getFile(), blocoId, arquivo.getDescritor());
	}	
	
	public IBloco getBloco(byte containerId, int blocoId, Descritor descritor){
		return getBloco(generateFile(containerId), blocoId, descritor);
	}
	
	private IBloco getBloco(File file, int blocoId, Descritor descritor){
		
		byte[] bloco = IO_Operations
				.readFromFile(file, blocoPosition(blocoId), BlocoControle.TAMANHO_BLOCO);
		
		try {
			if(bloco[4] == ETipoBlocoArquivo.dados.getValor()){
				return new Bloco(bloco, descritor);				
			}else if(bloco[4] == ETipoBlocoArquivo.indices.getValor()){
				return new Node(bloco, descritor);		
			}
		} catch (IncorrectFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void atualziarBlocoControle(File file, BlocoControle b){
		IO_Operations.writeFile(file, b.getByteArray(), 0);
	}
	
	protected void EscreverBloco(File file, IBloco b) {
		try {
			IO_Operations.writeFile(file, b.getByteArray(), blocoPosition(b.getBlocoId()));
		} catch (IncorrectFormatException e) {
			e.printStackTrace();
		}		
	}
	
	protected void EscreverBloco(IBloco b) {		
 		EscreverBloco(generateFile(b.getBlocoTupleId().getContainerId()), b);
 	}
	
	private int blocoPosition(int blocoId){
		return BlocoControle.TAMANHO_BLOCO * blocoId;
	}

	public String getArquivoString(IArquivo a) {
		return a.toString();
	}
	
	public ArrayList<IArquivo> getTabelas(){
		ArrayList<IArquivo> arquivos = new ArrayList<IArquivo>();
		
		for(File f : DISC_PATH.listFiles()){
			arquivos.add(loadArquivoFromCache(f));
		}
		
		return arquivos;
	}
	
	public IArquivoEvents getArquivoEvents() {
		return ArquivoEvents;
	}
}

























