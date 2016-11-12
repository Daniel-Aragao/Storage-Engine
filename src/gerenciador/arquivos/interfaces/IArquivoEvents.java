package gerenciador.arquivos.interfaces;

import gerenciador.arquivos.blocos.Bloco;

public interface IArquivoEvents {
	public void BlocoAdicionado(IArquivo a, IBloco b);
	public void BlocoRemovido(IArquivo a, IBloco b);
	public Bloco RequisitarBloco(IArquivo a, int blocoId);
	public void BlocoAlterado(IArquivo a, IBloco b);
}
