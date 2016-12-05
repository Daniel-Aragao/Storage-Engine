package gerenciador.arquivos.interfaces;

import java.io.File;

import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;

public interface IArquivo extends IBinarizable<IArquivo> {

	Descritor getDescritor();

	File getFile();

	void setArquivoEvent(IArquivoEvents arquivoEvents);

	void addBloco(IBloco bloco);

	void removerBloco(IBloco bloco);

	void AdicionarLinha(ITupla tupla);

	void RemoverLinha(ITupla tupla);

	BlocoControle getBlocoControle();

	byte getId();

	String getNome();

	ETipoBlocoArquivo getTipo();

	boolean hasIndice(byte id);

	int qtdIndices();

	void setQtdIndice(byte byteArrayToInt);

	void atualizar();

	IBlocoEvents getBlocoEvents();

}