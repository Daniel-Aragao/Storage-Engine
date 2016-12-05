package gerenciador.arquivos.interfaces;

import gerenciador.RowId;

public interface IArquivoEvents {
	public void BlocoAdicionado(IArquivo a, IBloco b);
	public void BlocoAdicionado(IBloco b);
	
	public void BlocoRemovido(IArquivo a, IBloco b);
	public void BlocoRemovido(IBloco b);
	
	public IBloco RequisitarBloco(IArquivo a, int blocoId);
	public IBloco RequisitarBloco(RowId rowid);
	
	public void BlocoAlterado(IArquivo a, IBloco b);
	public void BlocoAlterado(IBloco bloco);
	
	public void BlocoControleAlterado(IArquivo a);
	
	public void InserirEventos(IBloco bloco);
}
