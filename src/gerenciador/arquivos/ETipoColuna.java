package gerenciador.arquivos;

public enum ETipoColuna {
	string(0), inteiro(1);
	
	public int valor;
	
	ETipoColuna(int valor) {
		this.valor = valor;
	}
	
	public byte getValor(){
		return (byte)valor;
	}
	
}
