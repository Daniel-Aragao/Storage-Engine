package gerenciador.arquivos.blocos.components;

public class Coluna {
	
	private short columnSize;
	private byte[] dados;
	
	public Coluna(byte[] coluna){
		setColumnSize((short) coluna.length);
		setData(coluna);
	}
	
	public short getColumnSize() {
		return columnSize;
	}
	
	private void setColumnSize(short columnSize) {
		this.columnSize = columnSize;
	}
	
	private void setData(byte[] dado){
		this.dados = dado;
	}
	
	public byte[] getDado(){
		return dados;
	}
}
