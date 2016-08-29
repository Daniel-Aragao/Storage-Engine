package gerenciador.arquivos.blocosControle;

import gerenciador.arquivos.ETipoColuna;
import gerenciador.arquivos.exceptions.ByteArrayIncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class UnidadeDescricao implements IBinarizable<Descritor>{
//	descritor bloco de controle é representado por nome, tipo e tamanho de cada coluna
	public static final int UNIDADE_DESCRICAO_SIZE = 22;
	
	private String nome; // 20 bytes
	private ETipoColuna tipo; // 1 byte
	private byte tamanho; // 1 byte

	public UnidadeDescricao() throws ByteArrayIncorrectFormatException{
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws ByteArrayIncorrectFormatException {
		if(nome.length() > 20) 
			throw new ByteArrayIncorrectFormatException("Nome não deve ultrapassar 10 caractéres");
		
		this.nome = nome;
	}

	public ETipoColuna getTipo() {
		return tipo;
	}

	public void setTipo(ETipoColuna tipo) {
		this.tipo = tipo;
	}

	public byte getTamanho() {
		return tamanho;
	}

	public void setTamanho(byte tamanho) {
		this.tamanho = tamanho;
	}

	@Override
	public byte[] getByteArray() {
		byte[] retorno = new byte[UnidadeDescricao.UNIDADE_DESCRICAO_SIZE];
		ByteArrayTools.appendArrays(retorno, getNome().getBytes());
				
		retorno[20] = this.tipo.getValor();
		retorno[21] = this.tamanho;
				
		return retorno;
	}
	
	
}
