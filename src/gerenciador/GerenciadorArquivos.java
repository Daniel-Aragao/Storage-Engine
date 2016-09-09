package gerenciador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.loger.Log;

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
	
	
	public byte[] getBloco(String rowId){
		
		return null;
	}
	
	public GerenciadorArquivos() {
		Log.Write("GerenciadorArquivos");
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
				Arquivo arquivo = new Arquivo(blocoControle);	
				
				Log.Write("Gravar Arquivo");
				gravarArquivo(file, arquivo.getByteArray());
				
				return containerId;
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
	
	private void gravarArquivo(File file, byte[] byteArray) {
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			
			Log.Write("Escrever: "+ byteArray);
			raf.write(byteArray, 0, byteArray.length);
			
		} catch (FileNotFoundException e) {
			Log.Erro("Falha ao encontrar o arquivo");
			e.printStackTrace();
		} catch (IOException e) {
			Log.Erro("Falha ao escrever stream de bytes");
			e.printStackTrace();
		}
		
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
	
	public void AdicionarLinha(byte containerId, String tupla){
		File file = generateFile(containerId);
		
		if(file.exists()){
			Log.Erro("Arquivo inexistente");
			throw new RuntimeException("Arquivo inexistente");
		}
		
//		RandomAccessFile raf = new RandomAccessFile(file, "r");
		
		
		
	}
	
//	public int CreateFile()
}
