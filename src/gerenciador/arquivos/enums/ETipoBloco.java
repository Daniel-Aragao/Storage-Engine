package gerenciador.arquivos.enums;

public enum ETipoBloco {
	dados(0),
	indices(1);
	
	public int valor;
	
	private ETipoBloco(int val) {
		valor = val;
	}
	
	public byte getValor(){
		return (byte)valor;
	}
	
	public static ETipoBloco getByValue(int value){
		for(ETipoBloco e : ETipoBloco.values()){
			if(e.valor == value){
				return e;
			}
		}
		return null;
	}
}
