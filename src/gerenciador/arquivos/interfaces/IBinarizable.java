package gerenciador.arquivos.interfaces;

import gerenciador.arquivos.exceptions.IncorrectFormatException;

public interface IBinarizable <T>{
	public byte[] getByteArray() throws IncorrectFormatException;
	public void fromByteArray(byte[] dados) throws IncorrectFormatException;
}
