package gerenciador.indice.blocos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import gerenciador.GerenciadorBuffer;
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
		throw new RuntimeException("Não implementado");
	}
	
	public RowId getSubArvore(Chave tupla) {
		for(int i = 0; i < chaves.size(); i++){
			Chave chave = chaves.get(i);
			if (compareChave(tupla, chave) <= 0){
				return ponteiros.get(i);
			}else if(i+1 == chaves.size()){ // caso da última chave
				return ponteiros.get(i+1);
			}
		}
		throw new RuntimeException("Não implementado");
	}
	
	public int compareChave(Chave chave1, Chave chave2){
		ArrayList<Coluna> colunas1 = chave1.getColunas();
		ArrayList<Coluna> colunas2 = chave1.getColunas();
		
		if(colunas1.size() != colunas2.size()){
			throw new RuntimeException("Chaves incompatíveis");
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
		chaves.add((Chave) tupla);
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		throw new RuntimeException("Não implementado");
		
	}

	@Override
	public int size() {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public ArrayList<ITupla> getTuplas() {
		throw new RuntimeException("Não implementado");
	}

	public void ordenarChaves() {
		
		Collections.sort(chaves, new Comparator<Chave>(){
		  public int compare(Chave c1, Chave c2){
		    return compareChave(c1,c2);
		  }
		});			
	}

	public void ordenarPonteiros(GerenciadorBuffer buffer) {
		Collections.sort(ponteiros, new Comparator<RowId>(){
			  public int compare(RowId r1, RowId r2){
				  Chave c1 = ((Node)buffer.getBloco(r1)).menorChave();
				  Chave c2 = ((Node)buffer.getBloco(r2)).menorChave();
			    return compareChave(c1,c2);
			  }
			});			
	}

	public Chave getChave(int i) {
		return this.chaves.get(i);
	}

	public ArrayList<Chave> getMetadeChaves() {
		ArrayList<Chave> retorno = new ArrayList<Chave>();
		
		int total = chaves.size();
		
		int metade = total/2;
		metade = (total%2 != 0)? (metade - 1):metade;
		
		retorno.add(chaves.get(metade));
		
		for(int i = metade + 1; i < total; i++){
			retorno.add(chaves.remove(i));
		}		
		
		return retorno;
	}
	
	public ArrayList<RowId> getMetadePonteiros(){
		ArrayList<RowId> retorno = new ArrayList<RowId>();
		
		int total = ponteiros.size();
		int metade = total/2;
		
		retorno.add(ponteiros.get(metade));
		
		for(int i = metade + 1; i < total; i++){
			retorno.add(ponteiros.remove(i));
		}		
		
		return retorno; 
//		throw new RuntimeException("Não implementado");
	}

	public void addPonteiro(RowId ri) {
		this.ponteiros.add(ri);
		
	}

}
