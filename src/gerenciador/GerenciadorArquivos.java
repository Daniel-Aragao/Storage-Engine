package gerenciador;

import java.io.File;
import java.io.IOException;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.loger.Log;

public class GerenciadorArquivos {
	public static final char CARACTERE_SEPARADOR = '|',
							CARACTERE_STRING = 'A',
							CARACTERE_INTEIRO = 'I';
	
	public static final String REGEX_COLUMN = "([^\\[]+)\\[(.*)\\((.*)\\)\\]";
//							CARACTERE_TIPO[]= {'[',']'},
//							CARACTERE_TAMANHO[]= {'(',')'},
	
	
	public static final File DISC_PATH = 
			new File("C:\\Users\\danda_000\\git\\Storage-Engine\\res\\Disco");
	
	
	public byte[] getBloco(String rowId){
		
		return null;
	}
	
	public void CriarArquivo(String propriedades){
		String [] props = propriedades.split(CARACTERE_SEPARADOR+"");
		
		criarDiret�rio();
		
		try{
			
			BlocoControle blocoControle = new BlocoControle(props, getNextContainerId());
			Arquivo arquivo = new Arquivo(blocoControle);			
		}catch(IncorrectFormatException e){
			
			Log.Erro(e.getMessage());
			Log.Write("O arquivo n�o foi criado");
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

	private byte getNextContainerId(){
		byte newContainerId = 1;
		File f = null;
		
		for(byte i = 0; i <= 255 ;){
			f = new File(GerenciadorArquivos.DISC_PATH.getAbsolutePath()+"\\tabela-"+i+".txt");
			if(!f.exists()){
				try {
					f.createNewFile();
					return i;
				} catch (IOException e) {
					Log.Erro("IOException");
					e.printStackTrace();
				}
			}
		}
		Log.Erro("DISC_PATH atingiu o m�ximo de arquivos");
		throw new RuntimeException("DISC_PATH atingiu o m�ximo de arquivos");		
	}
	
	public void AdicionarLinha(String tupla){
		
	}
	
//	public int CreateFile()
}
