package gerenciador.arquivos.interfaces;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.HeaderBloco;
import gerenciador.arquivos.blocos.IDados;
import gerenciador.arquivos.exceptions.IncorrectFormatException;

public interface IBloco extends IBinarizable<IBloco>{

	void setEvents(IBlocoEvents events);

	HeaderBloco getHeader();

	int getBlocoId();

	IDados getDados();

	void addTupla(ITupla tupla);

	RowId getNextTupleId();

	void addTupla(byte[] tuplaBytes) throws IncorrectFormatException;

	void removeTupla(ITupla tupla);

	void removeTupla(int index);

	RowId getBlocoTupleId();

}