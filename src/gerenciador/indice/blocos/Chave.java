package gerenciador.indice.blocos;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.Coluna;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class Chave extends Tupla{
	
	private RowId target;
	public Chave(Coluna[] props, RowId tupleId, Descritor descritor, RowId rowid)throws IncorrectFormatException{
		super(props, tupleId, descritor);
		this.target = rowid;
//		this.size += RowId.ROWID_SIZE;
	}
	
	public Chave(String[] props, RowId tupleId, Descritor descritor, RowId rowid)throws IncorrectFormatException{
		super(props, tupleId, descritor);
		this.target = rowid;
//		this.size += RowId.ROWID_SIZE;
	}
	
	public Chave(byte[] dados, RowId tupleId, Descritor descritor) throws IncorrectFormatException {
		super(ByteArrayTools.subArray(dados, RowId.ROWID_SIZE, dados.length - RowId.ROWID_SIZE), 
				tupleId, 
				descritor);
		this.target = new RowId(ByteArrayTools.subArray(dados, RowId.ROWID_SIZE));
//		this.size += RowId.ROWID_SIZE;
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] ba = target.getByteArray();
		
		ba = ByteArrayTools.concatArrays(ba, super.getByteArray());
		
		return ba;
	}
	
	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
//		this.rowid = new RowId(ByteArrayTools.subArray(dados, RowId.ROWID_SIZE));

  		super.fromByteArray(dados);
  		
//  		this.size += RowId.ROWID_SIZE		
	}

	public RowId getTarget() {
		return target;
	}

	public void setTarget(RowId rowid) {
		this.target = rowid;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
