package gerenciador.arquivos.interfaces;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocos.Bloco;

public interface IArquivoEvents {
	public void BlocoAdicionado(Arquivo a, Bloco b);
	public void BlocoRemovido(Arquivo a, Bloco b);
	public Bloco RequisitarBloco(Arquivo a, int blocoId);
	public void BlocoAlterado(Arquivo a, Bloco b);
}
