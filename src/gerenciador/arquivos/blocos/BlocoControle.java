package gerenciador.arquivos.blocos;

import gerenciador.arquivos.exceptions.ByteArrayIncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;

public class BlocoControle implements IBinarizable<BlocoControle> {
	public static final int MIN_ARRAY_SIZE = 11;
	//HEADER 11b
// byte 0 - id do container
// byte 123 - size dos blocos = 4096b
// byte 4 - status do container
// byte 5, 6, 7, 8 - indica id do proximo bloco livre
// byte 9, 10 - tamanho do header do container (body do bloco de controle)
// descritor bloco de controle: nome, tipo, tamanho
// primeira linha ser� descri��o do arquivo
// cada linha do arquivo ser� uma linha da tabela 
	
	public BlocoControle(byte[] bytes)throws ByteArrayIncorrectFormatException {

	}
	
	@Override
	public byte[] getByteArray() {
		// TODO Auto-generated method stub
		return null;
	}
}
