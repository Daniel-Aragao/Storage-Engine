package gerenciador.arquivos.interfaces;

import java.util.ArrayList;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.Coluna;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.exceptions.IncorrectFormatException;

public interface ITupla extends IBinarizable<ITupla> {

	RowId getTupleId();

	void setTupleId(RowId tupleId);

	int getSize();

	ArrayList<Coluna> getColunas();

	Coluna getColuna(int index);

	Coluna createColuna(String prop, UnidadeDescricao descricao) throws IncorrectFormatException;

	Coluna createColuna(byte[] ba, UnidadeDescricao descricao) throws IncorrectFormatException;

}