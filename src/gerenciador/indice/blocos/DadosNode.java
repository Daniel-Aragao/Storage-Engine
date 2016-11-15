package gerenciador.indice.blocos;

import gerenciador.arquivos.blocos.IDados;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.ITupla;

public class DadosNode implements IDados{

	public DadosNode(Descritor descritor) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public ITupla getTupla(int index) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public int getSize() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public int getOffSet(int i) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public boolean contains(ITupla tupla) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public boolean contains(int index) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public void RemoveTupla(ITupla tupla) {
		throw new RuntimeException("Não implementado");
		
	}

	@Override
	public int RemoveTupla(int index) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public boolean isEmpty() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public void addTupla(ITupla tupla) {
		throw new RuntimeException("Não implementado");
		
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");
		
	}

	@Override
	public int size() {
		throw new RuntimeException("Não implementado");
	}

}
