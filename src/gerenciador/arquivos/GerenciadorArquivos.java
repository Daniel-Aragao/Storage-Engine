package gerenciador.arquivos;

import java.util.ArrayList;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.BlocoControle;

public class GerenciadorArquivos {
	private BlocoControle blocoControle;
	private ArrayList<Bloco> blocos;
	
	public GerenciadorArquivos(){
		blocos = new ArrayList<Bloco>();
		blocoControle = new BlocoControle();
	}
	
	public Bloco getBloco(String rowId){
		
		return null;
	}
}
