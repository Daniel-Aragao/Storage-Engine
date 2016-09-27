package gerenciador;

import java.io.File;
import java.io.IOException;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IArquivoEvents;
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
	
	private IArquivoEvents ArquivoEvents;
	
	public GerenciadorArquivos() {
		Log.Write("GerenciadorArquivos iniciado..");
		createArquivoEvents();
	}
	
	private void createArquivoEvents(){
		this.ArquivoEvents = new IArquivoEvents() {
			
			@Override
			public void BlocoRemovido(Arquivo a, Bloco b) {
				throw new RuntimeException("Não implementado");
				
			}
			
			@Override
			public void BlocoAdicionado(Arquivo a, Bloco b) {
				throw new RuntimeException("Não implementado");
				
			}

			@Override
			public Bloco RequisitarBloco(Arquivo a, int blocoId) {
				throw new RuntimeException("Não implementado");
			}

			@Override
			public void BlocoAlterado(Arquivo a, Bloco b) {
				throw new RuntimeException("Não implementado");
				
			}
		};
	}
	
	public byte CriarArquivo(String propriedades)	{
		Log.Write("Iniciar criação de arquivo");
		
		String [] props = propriedades.split(CARACTERE_SEPARADOR);
		
		Log.Write("Criar diretório");
		
		criarDiretório();
		
		Log.Write("Pegar container id");
		
		byte containerId =  getNextContainerId();
		
		Log.Write("Containerid: "+containerId);
		
		File file = null;
		
		try{
			file = generateFile(containerId);
			// certificar que o arquivo vai ser criado
			if(file.createNewFile()){
				Log.Write("Criar bloco de controle");
				BlocoControle blocoControle = new BlocoControle(props, containerId);
				
				Log.Write("Criar Arquivo");
				Arquivo arquivo = new Arquivo(blocoControle, file);
				arquivo.setArquivoEvent(ArquivoEvents);
				
				Log.Write("Gravar Arquivo");
				IO_Operations.writeFile(file, arquivo.getByteArray(), 0);
				
				return containerId;
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
		return -1;
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

	private File generateFile(byte i){
		return new File(GerenciadorArquivos.DISC_PATH.getAbsolutePath()+"\\tabela-"+i+".txt");
	}		
	
	private byte getNextContainerId(){
		File f = null;
		
		for(byte i = 0; i <= 255 ;i++){
			f = generateFile(i);
			if(!f.exists()){
				return i;
			}
		}
		Log.Erro("DISC_PATH atingiu o máximo de arquivos");
		throw new RuntimeException("DISC_PATH atingiu o máximo de arquivos");		
	}
	
	public void AdicionarLinha(byte containerId, String props){
		Log.Write("Iniciar adição de linha");
		Log.Write("Verificando existência do container/arquivo");
		File file = generateFile(containerId);
		
		if(!file.exists()){
			Log.Erro("Arquivo inexistente");
			throw new RuntimeException("Arquivo inexistente");
		}
		// não esquecer o rowId
		//
		Log.Write("Inicializando o arquivo encontrado");
		Arquivo arquivo = new Arquivo(getBlocoControle(file), file);
		arquivo.setArquivoEvent(ArquivoEvents);
		
		Tupla tupla = null;
		try {
			Log.Write("Montando tupla...");
			tupla = new Tupla(props.split(CARACTERE_SEPARADOR), arquivo.getDescritor());
			
			Log.Write("Adicionando linha");
			arquivo.AdicionarLinha(tupla);
			
		} catch (IncorrectFormatException e) {
			Log.Erro(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void gravarBloco(Bloco bloco){
		Log.Write("Gravando bloco");
	}
	
	private BlocoControle getBlocoControle(File file){
		byte[] bloco = IO_Operations
				.readFromFile(file, 0, BlocoControle.TAMANHO_BLOCO);
			
		BlocoControle blocoControle = new BlocoControle(bloco);
		return blocoControle;
	}
	
	private Bloco getBloco(File file, BlocoControle blocoControle){
		Bloco retorno = null;
		
		int prox = blocoControle.getHeader().getProxBlocoLivre();
		if (prox != 1 ){
			prox--;
		}
		
		
//		if(blockId == 1){
//			return new Bloco(containerId, blockId, ETipoBloco.dados);
//		}
		
		return retorno;
	}
}

























