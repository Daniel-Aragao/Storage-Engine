package gerenciador.arquivos.blocos;

import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class Coluna implements IBinarizable<Coluna> {

	private short columnSize;
	private byte[] dados;

	public Coluna(byte[] coluna) {
		setColumnSize((short) coluna.length);
		setData(coluna);
	}

	public short getColumnSize() {
		return columnSize;
	}

	private void setColumnSize(short columnSize) {
		this.columnSize = columnSize;
	}

	private void setData(byte[] dado) {
		this.dados = dado;
	}

	public byte[] getDado() {
		return dados;
	}

	@Override
	public byte[] getByteArray() {
		byte[] retorno = new byte[2];

		byte[] columnsize = ByteArrayTools.intToByteArray(this.columnSize);
		retorno[0] = columnsize[2];
		retorno[1] = columnsize[3];

		return ByteArrayTools.concatArrays(retorno, dados);
	}
}
