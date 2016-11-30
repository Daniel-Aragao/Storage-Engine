package gerenciador.arquivos.blocos;

import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;

public interface IHeader extends IBinarizable<IHeader> {

	boolean isFull(int size);

	byte[] getByteArray() throws IncorrectFormatException;

	int getBytesUsados();

	void setBytesUsados(int bytesUsados);

	void incBytes(int bytes);

	void decBytes(int bytes);

	byte getContainerId();

	int getBlocoId();

	ETipoBlocoArquivo getTipo();

	void fromByteArray(byte[] dados) throws IncorrectFormatException;

}