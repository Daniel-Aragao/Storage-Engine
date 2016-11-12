package gerenciador.arquivos.interfaces;

import java.io.File;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;

public interface IArquivo extends IBinarizable<IArquivo> {

	Descritor getDescritor();

	File getFile();

	void setArquivoEvent(IArquivoEvents arquivoEvents);

	void addBloco(Bloco bloco);

	void removerBloco(IBloco bloco);

	void AdicionarLinha(ITupla tupla);

	void RemoverLinha(ITupla tupla);

	BlocoControle getBlocoControle();

	byte getId();

}