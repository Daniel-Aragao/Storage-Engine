package gerenciador;

import java.io.File;
import java.io.IOException;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.enums.ETipoBloco;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
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
	
	
	public byte[] getBloco(String rowId){
		
		return null;
	}
	
	public GerenciadorArquivos() {
		Log.Write("GerenciadorArquivos iniciado..");
	}
	
	public byte CriarArquivo(String propriedades)	{
		Log.Write("Iniciar cria��o de arquivo");
		
		String [] props = propriedades.split(CARACTERE_SEPARADOR);
		
		Log.Write("Criar diret�rio");
		
		criarDiret�rio();
		
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
				IO_Operations.writeFile(file, arquivo.getByteArray(), 0);
				
				return containerId;
			}else{
				Log.Write("Erro ao criar o arquivo");
				throw new RuntimeException("Erro ao criar o arquivo");
			}
		}catch(IncorrectFormatException e){		
			//Erros internos a gera��o dos blocos
			Log.Erro(e.getMessage());
			Log.Erro("Erro ao gerar o arquivo");
			
			Log.Write("O arquivo n�o foi criado");
			//Garantir que o arquivo ser� deletado ap�s algum erro na cria��o			
			for(int j = 0; j < 10 && !file.delete();j++);
			
			file = null;			
			
		} catch (IOException e) {
			//Erro na cria��o do arquivo
			Log.Erro("Erro ao criar o arquivo");
			e.printStackTrace();
			file = null;
		}
		return -1;
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
		Log.Erro("DISC_PATH atingiu o m�ximo de arquivos");
		throw new RuntimeException("DISC_PATH atingiu o m�ximo de arquivos");		
	}
	
	public void AdicionarLinha(byte containerId, String tupla){
		File file = generateFile(containerId);
		
		if(!file.exists()){
			Log.Erro("Arquivo inexistente");
			throw new RuntimeException("Arquivo inexistente");
		}
		// n�o esquecer o rowId
		//
		
		/* uma instancia de Arquivo deve ser gerada com parametro do tipo File
		 * o arquivo deve localizar seu BlocoControle no file injetado
				BlocoControle blocoControle = getBlocoControle(file);
		 * arquivo.adicionarLinha(props) deve ser chamado
				String [] props = tupla.split(CARACTERE_SEPARADOR);				
		 * deve ser localizado o pr�ximo bloco de inser��o
		 * tentativa de inserir no bloco a linha
		 * se poss�vel adicionar atualizar header do bloco
		 * se o bloco n�o for encontrado ou n�o tem mais espa�o
		 * instanciar novo bloco e atualizar header do bloco de controle  
				
		 * */
		
		/* pegar bloco de controle
		 * checar qual pr�ximo bloco livre
		 * se prox != 1 ent�o pegar prox-1
		 * pegando o bloco da mem�ria e tentar adicionar tupla nele
		 * montar tupla para se calcular seu tamanho, como indicado nos 4 primeiros bytes da tupla
		 * verificar se a linha montada cabe no bloco e se n�o couber 
		 * ent�o deve lan�ar BlockOverFlowException(mensagem, id do bloco que lan�ou)
		 * caso caiba a tupla ser� adicionada e o header do bloco atualizado
		 * o bloco ser� reescrito por cima de seus dados anteriores
		 * caso o bloco n�o possa conter os dados ou prox == 1
		 * ent�o ser� criado um bloco novo
		 * adicionar tupla ao bloco novo
		 * atualizar header do bloco novo
		 * atualizar header do bloco de controle
		 * regravar bloco de controle
		 * gravar bloco novo
		 * */
		
		
//		Bloco bloco = getBloco(file, blocoControle);
		
		
		
		
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

























