package gerenciador.arquivos.interfaces;

public interface IArquivoEvents {
	public void BlocoAdicionado(IArquivo a, IBloco b);
	public void BlocoRemovido(IArquivo a, IBloco b);
	public IBloco RequisitarBloco(IArquivo a, int blocoId);
	public void BlocoAlterado(IArquivo a, IBloco b);
	public void BlocoControleAlterado(IArquivo a);
}
