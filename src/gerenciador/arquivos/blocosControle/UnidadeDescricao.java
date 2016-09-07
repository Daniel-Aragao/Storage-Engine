package gerenciador.arquivos.blocosControle;

import gerenciador.arquivos.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class UnidadeDescricao implements IBinarizable<Descritor>{
//	descritor bloco de controle é representado por nome, tipo e tamanho de cada coluna
	public static final int UNIDADE_DESCRICAO_SIZE = 22,
			NOME_SIZE = 20,
			TIPO_SIZE = 1,
			TAMANHO_SIZE = 1;
	
	
	private String nome; // 20 bytes
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
		if(nome.length() > NOME_SIZE) 
			throw new IncorrectFormatException("Nome não deve ultrapassar 10 caractéres");
		
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
		ByteArrayTools.appendArrays(retorno, nomeBA);
		retorno[20] = this.tipo.getValor();
		retorno[21] = this.tamanho;
				
		return retorno;
	}
	
	
}
