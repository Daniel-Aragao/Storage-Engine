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
	
	public void AdicionarLinha(byte containerId, String tupla){
		File file = generateFile(containerId);
		
		if(!file.exists()){
			Log.Erro("Arquivo inexistente");
			throw new RuntimeException("Arquivo inexistente");
		}
		// não esquecer o rowId
		//
		
		/* uma instancia de Arquivo deve ser gerada com parametro do tipo File
		 * o arquivo deve localizar seu BlocoControle no file injetado
				BlocoControle blocoControle = getBlocoControle(file);
		 * arquivo.adicionarLinha(props) deve ser chamado
				String [] props = tupla.split(CARACTERE_SEPARADOR);				
		 * deve ser localizado o próximo bloco de inserção
		 * tentativa de inserir no bloco a linha
		 * se possível adicionar atualizar header do bloco
		 * se o bloco não for encontrado ou não tem mais espaço
		 * instanciar novo bloco e atualizar header do bloco de controle  
				
		 * */
		
		/* pegar bloco de controle
		 * checar qual próximo bloco livre
		 * se prox != 1 então pegar prox-1
		 * pegando o bloco da memória e tentar adicionar tupla nele
		 * montar tupla para se calcular seu tamanho, como indicado nos 4 primeiros bytes da tupla
		 * verificar se a linha montada cabe no bloco e se não couber 
		 * então deve lançar BlockOverFlowException(mensagem, id do bloco que lançou)
		 * caso caiba a tupla será adicionada e o header do bloco atualizado
		 * o bloco será reescrito por cima de seus dados anteriores
		 * caso o bloco não possa conter os dados ou prox == 1
		 * então será criado um bloco novo
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

























