package gerenciador.arquivos.enums;

import gerenciador.GerenciadorArquivos;

public enum ETipoColuna {
	string(GerenciadorArquivos.CARACTERE_STRING), inteiro(GerenciadorArquivos.CARACTERE_INTEIRO);
	
	public int valor;
	
	ETipoColuna(int valor) {
		this.valor = valor;
	}
	
	public byte getValor(){
		return (byte)valor;
	}
	
	public static ETipoColuna getByValue(int value){
		for(ETipoColuna e : ETipoColuna.values()){
			if(e.valor == value){
				return e;
			}
		}
		return null;
	}
	
}
