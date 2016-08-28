package gerenciador.arquivos.blocos;

import gerenciador.arquivos.exceptions.ByteArrayIncorrectFormatException;

public class Bloco {
	// header
//byte 0 id do container
//byte 1, 2, 3 id do bloco
//	vai vir o dado ou ele estará já identificado?
//byte 4 tipo do bloco
//	o que é o tipo do bloco?
			
//byte 5, 6, 7 bytes usados no bloco
	private byte[] Size; //preciso salvar mesmo como byte array ou posso criar objetos e tipos primários para quando for
//	requisitado devolver bytes?
// o método de adicionar linha deve conferir se há bytes disponíveis
	public Bloco(byte[] Headerbytes)throws ByteArrayIncorrectFormatException{
		Size = new byte[3];
	}
}
