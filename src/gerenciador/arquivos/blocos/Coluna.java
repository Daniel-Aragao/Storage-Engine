package gerenciador.arquivos.blocos;

import gerenciador.arquivos.interfaces.IBinarizable;

public abstract class Coluna implements IBinarizable<Coluna> {

	private short columnSize;

	
	public Coluna(short columnSize) {
		this.columnSize = columnSize;
	}

	public short getColumnSize() {
		return columnSize;
	}
	
	public abstract Object getDado();
	
	@Override
	public String toString() {
		return getDado().toString();
	}
}
