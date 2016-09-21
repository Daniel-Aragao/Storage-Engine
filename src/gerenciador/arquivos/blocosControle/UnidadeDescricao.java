package gerenciador.arquivos.blocosControle;

import gerenciador.arquivos.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class UnidadeDescricao implements IBinarizable<Descritor>{
//	descritor bloco de controle é representado por nome, tipo e tamanho de cada coluna
	public static final int NOME_SIZE = 30, // a mudança refletirá no resultado dos testes com NOME_SIZE = 30
			TIPO_SIZE = 1,
			TAMANHO_SIZE = 1,
			UNIDADE_DESCRICAO_SIZE = NOME_SIZE + TIPO_SIZE + TAMANHO_SIZE;
	
	
	private String nome; // 30 bytes
	private ETipoColuna tipo; // 1 byte
	private byte tamanho; // 1 byte

	public UnidadeDescricao(String nome, ETipoColuna tipo, byte tamanho)throws IncorrectFormatException{
		setNome(nome);
		setTipo(tipo);
		setTamanho(tamanho);
	}

	public String getNome() {
		return nome;
	}

	private void setNome(String nome) throws IncorrectFormatException {
		if(nome.length() > NOME_SIZE/2) 
			throw new IncorrectFormatException("Nome não deve ultrapassar "+NOME_SIZE/2+" caractéres");
		
		this.nome = nome;
	}

	public ETipoColuna getTipo() {
		return tipo;
	}

	private void setTipo(ETipoColuna tipo) {
		this.tipo = tipo;
	}

	public byte getTamanho() {
		return tamanho;
	}

	private void setTamanho(byte tamanho) {
		this.tamanho = tamanho;
	}

	@Override
	public byte[] getByteArray() {
		byte[] retorno = new byte[UnidadeDescricao.UNIDADE_DESCRICAO_SIZE];
		
		byte[] nomeBA = ByteArrayTools.stringToByteArray(getNome(), NOME_SIZE);
		ByteArrayTools.appendArrays(retorno, nomeBA, 0);
		retorno[NOME_SIZE] = this.tipo.getValor();
		retorno[NOME_SIZE+1] = this.tamanho;
				
		return retorno;
	}
	
	
}
