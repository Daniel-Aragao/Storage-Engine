package gerenciador.arquivos.blocos;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.exceptions.ByteArrayIncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;

public class Bloco implements IBinarizable<Arquivo>{
	// header
//byte 0 id do container
//byte 1, 2, 3 id do bloco
//	vai vir o dado ou ele estar� j� identificado?
//byte 4 tipo do bloco
//	o que � o tipo do bloco?
			
//byte 5, 6, 7 bytes usados no bloco
	private byte[] Size; //preciso salvar mesmo como byte array ou posso criar objetos e tipos prim�rios para quando for
//	requisitado devolver bytes?
// o m�todo de adicionar linha deve conferir se h� bytes dispon�veis
	public Bloco(byte[] Headerbytes)throws ByteArrayIncorrectFormatException{
		Size = new byte[3];
	}
@Override
public byte[] getByteArray() {
	// TODO Auto-generated method stub
	return null;
}
}
