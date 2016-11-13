package gerenciador.indice.blocos;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class Chave extends Tupla{
	
	private RowId rowid;
	
	public Chave(byte[] dados, RowId tupleId, Descritor descritor) throws IncorrectFormatException {
		super(dados, tupleId, descritor);
		throw new RuntimeException("Não implementado");
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] ba = rowid.getByteArray();
		
		ba = ByteArrayTools.concatArrays(ba, super.getByteArray());
		
		return ba;
	}
	
	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		this.rowid = new RowId(ByteArrayTools.subArray(dados, RowId.ROWID_SIZE));
		
		super.fromByteArray(ByteArrayTools.subArray(dados, RowId.ROWID_SIZE, dados.length - RowId.ROWID_SIZE));
		
	}
}
