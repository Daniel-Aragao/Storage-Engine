package gerenciador.indice.blocos;

import java.util.ArrayList;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.IDados;
import gerenciador.arquivos.blocos.IHeader;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.IBlocoEvents;
import gerenciador.arquivos.interfaces.ITupla;

public class Node implements IBloco {
	public static final int HEADER_BLOCO_INDICE_SIZE = 8;

	private ArrayList<RowId> ponteiro;
	private ArrayList<Chave> chaves;
	
	private Descritor descritor;
	private IBlocoEvents events;
	private DadosNode dados;
//	k, p e n(ordem)
	private HeaderNode header;

	public Node(byte containerId, int BlockId, ETipoBlocoArquivo tipoBloco, Descritor descritor)
			throws IncorrectFormatException {
		this.descritor = descritor;
		if (tipoBloco != ETipoBlocoArquivo.indices) {
			throw new IncorrectFormatException("Tipo de bloco deve ser de indice");
		}
//		header = new HeaderNode(containerId, BlockId, tipoBloco);
//		dados = new DadosNode(descritor);
	}

	public Node(byte[] dados, Descritor descritor) throws IncorrectFormatException {
		this.descritor = descritor;
		this.fromByteArray(dados);
	}

	@Override
	public void setEvents(IBlocoEvents events) {
		this.events = events;

	}

	@Override
	public IHeader getHeader() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public int getBlocoId() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public IDados getDados() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public void addTupla(ITupla tupla) {
		throw new RuntimeException("Não implementado");

	}

	@Override
	public RowId getNextTupleId() {
		RowId tupleId = new RowId(this.header.getContainerId(), getBlocoId(), this.header.getBytesUsados());

		return tupleId;
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
		return new RowId(this.header.getContainerId(), this.getBlocoId(), -1);
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		// intercalar cada chave com um rowid
		throw new RuntimeException("Não implementado");
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		// intercalados chaves com rowid
		throw new RuntimeException("Não implementado");

	}
}
