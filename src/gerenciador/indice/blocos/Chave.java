package gerenciador.indice.blocos;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.Coluna;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class Chave extends Tupla{
	
	private RowId rowid;
	public Chave(Coluna[] props, RowId tupleId, Descritor descritor, RowId rowid)throws IncorrectFormatException{
		super(props, tupleId, descritor);
		this.rowid = rowid;
		this.size += RowId.ROWID_SIZE;
	}
	
	public Chave(byte[] dados, RowId tupleId, Descritor descritor) throws IncorrectFormatException {
		super(ByteArrayTools.subArray(dados, RowId.ROWID_SIZE, dados.length - RowId.ROWID_SIZE), 
				tupleId, 
				descritor);
		this.rowid = new RowId(ByteArrayTools.subArray(dados, RowId.ROWID_SIZE));
		this.size += RowId.ROWID_SIZE;
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] ba = rowid.getByteArray();
		
		ba = ByteArrayTools.concatArrays(ba, super.getByteArray());
		
		return ba;
	}
	
	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		
		
	}

	public RowId getRowid() {
		return rowid;
	}

	public void setRowid(RowId rowid) {
		this.rowid = rowid;
	}
}
