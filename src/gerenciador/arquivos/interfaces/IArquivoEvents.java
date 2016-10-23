package gerenciador.arquivos.interfaces;

import gerenciador.RowId;
import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocos.Bloco;

public interface IArquivoEvents {
	public void BlocoAdicionado(Arquivo a, Bloco b);
	public void BlocoAdicionado(Bloco b);
	
	public void BlocoRemovido(Arquivo a, Bloco b);
	public void BlocoRemovido(Bloco b);
	
	public Bloco RequisitarBloco(Arquivo a, int blocoId);
	public Bloco RequisitarBloco(RowId rowid);
	
	public void BlocoAlterado(Arquivo a, Bloco b);
	public void BlocoAlterado(Bloco bloco);
	
}
