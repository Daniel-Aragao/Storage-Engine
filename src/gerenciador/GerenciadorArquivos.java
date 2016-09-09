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
	
	public void CriarArquivo(String propriedades)	{
		Log.Write("Iniciar cria��o de arquivo");
		
		String [] props = propriedades.split(CARACTERE_SEPARADOR);
		
		Log.Write("Criar diret�rio");
		
		criarDiret�rio();
		
		Log.Write("Pegar container id");
		
		byte containerId =  getNextContainerId();
		
		Log.Write("Containerid: "+containerId);
		
		File file = null;
		
		try{
			file = generateNewFile(containerId);
			// certificar que o arquivo vai ser criado
			if(file.createNewFile()){
				Log.Write("Criar bloco de controle");
				BlocoControle blocoControle = new BlocoControle(props, containerId);
				
				Log.Write("Criar Arquivo");
				Arquivo arquivo = new Arquivo(blocoControle);	
				
				Log.Write("Gravar Arquivo");
				gravarArquivo(file, arquivo.getByteArray());			
			}
		}catch(IncorrectFormatException e){		
			//Erros internos a gera��o dos blocos
			Log.Erro(e.getMessage());
			Log.Erro("Erro ao gerar o arquivo");
			
			Log.Write("O arquivo n�o foi criado");
			//Garantir que o arquivo ser� deletado ap�s algum erro na cria��o			
			for(int j = 0; j < 10 && !file.delete();j++);
			
		} catch (IOException e) {
			//Erro na cria��o do arquivo
			Log.Erro("Erro ao criar o arquivo");
			e.printStackTrace();
		}
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

	private void criarDiret�rio() {
		if(!DISC_PATH.exists()){
			Log.Write("Diret�rio inexistente");
			Log.Write("Criando diret�rio...");
			if(!DISC_PATH.mkdirs()){
				Log.Write("Erro ao criar diret�rio");
				throw new RuntimeException("Erro ao criar diret�rio");
			}
		}		
	}

	private File generateNewFile(byte i){
		return new File(GerenciadorArquivos.DISC_PATH.getAbsolutePath()+"\\tabela-"+i+".txt");
	}		
	
	private byte getNextContainerId(){
		File f = null;
		
		for(byte i = 0; i <= 255 ;i++){
			f = generateNewFile(i);
			if(!f.exists()){
				return i;
			}
		}
		Log.Erro("DISC_PATH atingiu o m�ximo de arquivos");
		throw new RuntimeException("DISC_PATH atingiu o m�ximo de arquivos");		
	}
	
	public void AdicionarLinha(String tupla){
		
	}
	
//	public int CreateFile()
}
