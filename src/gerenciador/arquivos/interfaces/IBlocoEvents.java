package gerenciador.arquivos.interfaces;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.Tupla;

public interface IBlocoEvents {
	public void blocoCheio(Tupla tupla);
	public void blocoVazio(Bloco bloco); 
}
