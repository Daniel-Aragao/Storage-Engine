package gerenciador.arquivos.blocos;

import java.util.ArrayList;

import gerenciador.RowId;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.arquivos.interfaces.ITupla;
import gerenciador.indice.blocos.Chave;

public interface IDados extends IBinarizable<IDados>{

	byte[] getByteArray() throws IncorrectFormatException;

	ITupla getTupla(int index);

	int getSize();

	int getOffSet(int i);

	boolean contains(ITupla tupla);

	boolean contains(int index);

	void RemoveTupla(ITupla tupla);

	int RemoveTupla(int index);

	boolean isEmpty();

	void addTupla(ITupla tupla);

	void fromByteArray(byte[] dados) throws IncorrectFormatException;

	int size();

	ArrayList<ITupla> getTuplas();

	ITupla getTupla(RowId rowid);

}