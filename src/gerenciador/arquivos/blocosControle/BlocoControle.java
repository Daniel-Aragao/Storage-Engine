package gerenciador.arquivos.blocosControle;

import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class BlocoControle implements IBinarizable<BlocoControle> {
	public static final int MIN_ARRAY_SIZE = 11, 
			HEADER_CONTROLE_SIZE = 11;
	public static final int TAMANHO_BLOCO = 4096, TRES_BYTES = 16777215;
	
	private HeaderControle header;
	private Descritor descritor;
	
	//atualizar tamanho do descritor ao acrescentar colunas
// descritor bloco de controle � representado por nome, tipo e tamanho de cada coluna
// primeira linha ser� descri��o do arquivo
// cada linha do arquivo ser� uma linha da tabela 
// deve validade se a linha inserida est� seguindo os conformes do descritor, 
	//como tipo e tamanho, por exemplo se a coluna id � do tipo int e n�o string
	
	public BlocoControle(String[] propriedades, byte containerId)throws IncorrectFormatException{		
		descritor = new Descritor(propriedades);
		header = new HeaderControle(containerId, descritor.getDescritorSize());		
	}
	
	public HeaderControle getHeader() {
		return header;
	}

	public Descritor getDescritor() {
		return descritor;
	}

	@Override
	public byte[] getByteArray() {
		return ByteArrayTools.concatArrays(
					header.getByteArray(), 
					descritor.getByteArray());
	}
}
