package gerenciador.arquivos.blocos;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocos.components.DadosBloco;
import gerenciador.arquivos.blocos.components.HeaderBloco;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class Bloco implements IBinarizable<Arquivo> {
	// header
	// byte 0 id do container
	// byte 1, 2, 3 id do bloco
	// vai vir o dado ou ele estará já identificado?
	// byte 4 tipo do bloco
	// o que é o tipo do bloco?

	// byte 5, 6, 7 bytes usados no bloco
	private byte[] Size; // preciso salvar mesmo como byte array ou posso criar
							// objetos e tipos primários para quando for
	// requisitado devolver bytes?
	// o método de adicionar linha deve conferir se há bytes disponíveis
	
	private HeaderBloco header;
	private DadosBloco dados;

	public Bloco(byte[] Headerbytes) throws IncorrectFormatException {
		Size = new byte[3];
	}

	@Override
	public byte[] getByteArray() {
		byte[] retorno;
		
		retorno =  ByteArrayTools.concatArrays(header.getByteArray(), dados.getByteArray());
		
		return retorno;		
	}
}
