package gerenciador.arquivos.enums;

public enum ETipoBlocoArquivo {
	dados(0),
	indices(1);
	
	public int valor;
	
	private ETipoBlocoArquivo(int val) {
		valor = val;
	}
	
	public byte getValor(){
		return (byte)valor;
	}
	
	public static ETipoBlocoArquivo getByValue(int value){
		for(ETipoBlocoArquivo e : ETipoBlocoArquivo.values()){
			if(e.valor == value){
				return e;
			}
		}
		return null;
	}
}
