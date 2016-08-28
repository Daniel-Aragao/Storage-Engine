package gerenciador.arquivos.blocosControle;

import gerenciador.arquivos.exceptions.ByteArrayIncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class BlocoControle implements IBinarizable<BlocoControle> {
	public static final int MIN_ARRAY_SIZE = 11, 
			HEADER_CONTROLE_SIZE = 11;
	public static final int MAX_ARRAY_SIZE = 4096;
	
	private HeaderControle header;
	private Descritor descritor;
	
	//HEADER 11b
// byte 0 - id do container
// byte 123 - size dos blocos = 4096b
// byte 4 - status do container
// byte 5, 6, 7, 8 - indica id do proximo bloco livre
// byte 9, 10 - tamanho do header do container (body do bloco de controle)
// descritor bloco de controle é representado por nome, tipo e tamanho de cada coluna
// primeira linha será descrição do arquivo
// cada linha do arquivo será uma linha da tabela 
// deve validade se a linha inserida está seguindo os conformes do descritor, 
	//como tipo e tamanho, por exemplo se a coluna id é do tipo int e não string
	
	public BlocoControle(byte[] bytes)throws ByteArrayIncorrectFormatException {
		header = new HeaderControle(bytes);
//		descritor = new HeaderControle(ArrayTools.subArray(bytes,, BlocoControle.MIN_ARRAY_SIZE));
	}
	
	@Override
	public byte[] getByteArray() {
		header.getByteArray();
		descritor.getByteArray();
		
		throw new RuntimeException();
	}
}
