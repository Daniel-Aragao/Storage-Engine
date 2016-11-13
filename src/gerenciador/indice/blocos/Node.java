package gerenciador.indice.blocos;

import java.util.ArrayList;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.DadosBloco;
import gerenciador.arquivos.blocos.HeaderBloco;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.IBlocoEvents;
import gerenciador.arquivos.interfaces.ITupla;

public class Node implements IBloco{
	
	private ArrayList<RowId> ponteiro;
	private ArrayList<Chave> chaves;


	@Override
	public void setEvents(IBlocoEvents events) {
		throw new RuntimeException("Não implementado");
		
	}

	@Override
	public HeaderBloco getHeader() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public int getBlocoId() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public DadosBloco getDados() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public void addTupla(ITupla tupla) {
		throw new RuntimeException("Não implementado");
		
	}

	@Override
	public RowId getNextTupleId() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public void addTupla(byte[] tuplaBytes) throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");
		
	}

	@Override
	public void removeTupla(ITupla tupla) {
		throw new RuntimeException("Não implementado");
		
	}

	@Override
	public void removeTupla(int index) {
		throw new RuntimeException("Não implementado");
		
	}

	@Override
	public RowId getBlocoTupleId() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");
	}
	
	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");
		
	}
}
