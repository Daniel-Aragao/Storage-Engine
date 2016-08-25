package gerenciador.arquivos.blocos;

import gerenciador.arquivos.exceptions.ByteArrayIncorrectFormatException;

public class Bloco {
	// header
//byte 0 id do container
//byte 1, 2, 3 id do bloco
//byte 4 tipo do bloco
//byte 5, 6, 7 bytes usados no bloco
	public Bloco(byte[] bytes)throws ByteArrayIncorrectFormatException{
		
	}
}
