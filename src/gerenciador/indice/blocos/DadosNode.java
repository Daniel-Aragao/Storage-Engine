package gerenciador.indice.blocos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import gerenciador.GerenciadorBuffer;
import gerenciador.RowId;
import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocos.Coluna;
import gerenciador.arquivos.blocos.IDados;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.ITupla;
import gerenciador.utils.ByteArrayTools;

public class DadosNode implements IDados{	

	private ArrayList<RowId> ponteiros;
	private ArrayList<Chave> chaves;
	private RowId vizinho;
	private RowId tupleBlocoId;
	private Descritor descritor;
	private HeaderNode header;
	

	public DadosNode(byte[] subArray, RowId blocoTupleId, Descritor descritor, HeaderNode header) throws IncorrectFormatException {
		this.descritor = descritor;
		this.tupleBlocoId = tupleBlocoId; 
		ponteiros = new ArrayList<RowId>();
		chaves = new ArrayList<Chave>();
		this.header = header;
		fromByteArray(subArray);
	}

	public DadosNode(Descritor descritor, HeaderNode header) {
		this.header = header;
		this.descritor = descritor;
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
		return null;
	}
	
	public static int compareChave(Chave chave1, Chave chave2){
		ArrayList<Coluna> colunas1 = chave1.getColunas();
		ArrayList<Coluna> colunas2 = chave1.getColunas();
		
		int comparacoes = colunas2.size();
		if(colunas2.size() < 1){
			throw new RuntimeException("A chave deve conter no mínimo um valor");
		}
		if(colunas1.size() < comparacoes){
			throw new RuntimeException("Chaves incompatíveis");
		}
		for(int i = 0; i < comparacoes; i++){
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
		return chaves.isEmpty();
	}

	@Override
	public void addTupla(ITupla tupla) {
		chaves.add((Chave) tupla);
	}
	
	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		
		byte[] retorno = new byte[0];
				
		for(ITupla t : chaves){
			retorno = ByteArrayTools.concatArrays(retorno, t.getByteArray());
		}
		
		return retorno;
	}
	
	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		int pointer = 0;
		
		for(int i = 0; i < header.getQtdPonteiros(); i++, pointer += RowId.ROWID_SIZE){
			RowId rowid = new RowId(ByteArrayTools.subArray(dados, pointer, RowId.ROWID_SIZE));
			ponteiros.add(rowid);
		}
		for(int i = 0;i < header.getQtdPonteiros()-1;i++){
			
			int chaveSize = ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, pointer, 4));
			byte[] chaveBA = ByteArrayTools.subArray(dados, pointer, chaveSize);
			
			RowId tupleId = new RowId(this.tupleBlocoId.getContainerId(), 
					this.tupleBlocoId.getBlocoId(), 
					pointer + Node.HEADER_BLOCO_INDICE_SIZE);
			
			Chave chave = new Chave(chaveBA, tupleId, descritor);
			
			chaves.add(chave);
			
			pointer += chaveSize;
		}
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
			header.decQtdPonteiros(1);
		}		
		
		return retorno; 
//		throw new RuntimeException("Não implementado");
	}

	public void addPonteiro(RowId ri) {
		this.ponteiros.add(ri);
		header.incQtdPonteiros(1);
	}

	public boolean hasPonteiro(Node noh) {
		for(RowId ri : ponteiros){
			if(noh.getBlocoTupleId().isSameBloco(ri)){
				return true;
			}
		}
		return false;
	}

	public boolean hasChave(Chave chave) {
		for(Chave c: chaves){
			if (compareChave(chave, c) == 0){
				return true;
			}
		}
		return false;
	}

	public RowId getBlocoId(Chave tupla) {
		for(int i = 0; i < chaves.size(); i++){
			Chave chave = chaves.get(i);
			if (compareChave(chave, tupla) == 0){
				return chave.getTupleId();
			}
		}
		return null;
	}

	public void setVizinho(RowId blocoTupleId) {
		vizinho = blocoTupleId;
		
	}

	public RowId getVizinho() {
		return vizinho;
	}

	public ArrayList<Chave> getChaves() {
		return chaves;
	}

	@Override
	public ITupla getTupla(RowId rowid) {
		throw new RuntimeException("Não implementado");
	}

}
