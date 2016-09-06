package gerenciador;

import java.io.File;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocosControle.BlocoControle;

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
		
		BlocoControle blocoControle = new BlocoControle(props);
		Arquivo arquivo = new Arquivo(blocoControle);
		
		
	}
	public void AdicionarLinha(String tupla){
		
	}
	
//	public int CreateFile()
}
