package gerenciador.arquivos.interfaces;

public interface IBlocoEvents {
	public void blocoCheio(ITupla tupla);
	public void blocoVazio(IBloco bloco); 
	public void blocoAlterado(IBloco bloco);
}
