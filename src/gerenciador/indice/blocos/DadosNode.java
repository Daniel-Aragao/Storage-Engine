package gerenciador.indice.blocos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.Coluna;
import gerenciador.arquivos.blocos.IDados;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.ITupla;

public class DadosNode implements IDados{	

	private ArrayList<RowId> ponteiros;
	private ArrayList<Chave> chaves;
	

	public DadosNode(Descritor descritor) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte [] array = new byte[BlocoControle.TAMANHO_BLOCO - HeaderNode.TAMANHO_HEADER];
		throw new RuntimeException("N�o implementado");
	}
	
	public RowId getSubArvore(Chave tupla) {
		for(int i = 0; i < chaves.size(); i++){
			Chave chave = chaves.get(i);
			if (compareChave(tupla, chave) <= 0){
				return ponteiros.get(i);
			}else if(i+1 == chaves.size()){
				return ponteiros.get(i+1);
			}
		}
		throw new RuntimeException("N�o implementado");
	}
	
	public int compareChave(Chave chave1, Chave chave2){
		ArrayList<Coluna> colunas1 = chave1.getColunas();
		ArrayList<Coluna> colunas2 = chave1.getColunas();
		
		if(colunas1.size() != colunas2.size()){
			throw new RuntimeException("Chaves incompat�veis");
		}
		for(int i = 0; i < colunas1.size(); i++){
			int comparacao = colunas1.get(i).compare(colunas2.get(i));
			if (comparacao != 0) return comparacao;
		}
		
		return 0;
	}
	
	public int getSizeChaves(){
		return chaves.size();
	}
	
	public int getSizePonteiros(){
		return ponteiros.size();
	}

	@Override
	public ITupla getTupla(int index) {
		throw new RuntimeException("N�o implementado");
	}

	@Override
	public int getSize() {
		throw new RuntimeException("N�o implementado");
	}

	@Override
	public int getOffSet(int i) {
		throw new RuntimeException("N�o implementado");
	}

	@Override
	public boolean contains(ITupla tupla) {
		throw new RuntimeException("N�o implementado");
	}

	@Override
	public boolean contains(int index) {
		throw new RuntimeException("N�o implementado");
	}

	@Override
	public void RemoveTupla(ITupla tupla) {
		throw new RuntimeException("N�o implementado");
		
	}

	@Override
	public int RemoveTupla(int index) {
		throw new RuntimeException("N�o implementado");
	}

	@Override
	public boolean isEmpty() {
		throw new RuntimeException("N�o implementado");
	}

	@Override
	public void addTupla(ITupla tupla) {
		throw new RuntimeException("N�o implementado");
		
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		throw new RuntimeException("N�o implementado");
		
	}

	@Override
	public int size() {
		throw new RuntimeException("N�o implementado");
	}

	@Override
	public ArrayList<ITupla> getTuplas() {
		throw new RuntimeException("N�o implementado");
	}

	public void ordenarFolha(Chave tupla) {
		
		Collections.sort(chaves, new Comparator<Chave>(){
		  public int compare(Chave c1, Chave c2){
		    return compareChave(c1,c2);
		  }
		});
		
		
		throw new RuntimeException("N�o implementado");		
	}

}
