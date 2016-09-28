package gerenciador.arquivos.blocos;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.enums.ETipoBloco;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.arquivos.interfaces.IBlocoEvents;
import gerenciador.utils.ByteArrayTools;

public class Bloco implements IBinarizable<Arquivo> {
	public static final int HEADER_BLOCO_SIZE = 8;

	private HeaderBloco header;
	private DadosBloco dados;
	
	private Descritor descritor;
	private IBlocoEvents events;

	public Bloco(byte containerId, int BlockId, ETipoBloco tipoBloco, Descritor descritor){
		this.descritor = descritor;
		header = new HeaderBloco(containerId, BlockId, tipoBloco);
		dados = new DadosBloco(descritor);
	}
	
	
	public Bloco(byte[] dados, Descritor descritor) throws IncorrectFormatException {
		this.descritor = descritor;
		fromByteArray(dados);
	}
	
	public void setEvents(IBlocoEvents events){
		this.events = events;
	}

	public HeaderBloco getHeader() {
		return header;
	}
	
	public int getBlocoId(){
		return header.getBlocoId();
	}
	
	public DadosBloco getDados() {
		return dados;
	}
	
	public void addTupla(Tupla tupla){
		if(this.getHeader().isFull(tupla.getSize())){
			if(events != null){
				events.blocoCheio(tupla);
			}
		}else{
			dados.addTupla(tupla);
			this.getHeader().incBytes(tupla.getSize());
			
			if(events != null){
				events.blocoAlterado(this);
			}
		}
	}
	
	public void addTupla(byte[] tuplaBytes) throws IncorrectFormatException{
		Tupla tupla = new Tupla(tuplaBytes, this.descritor);
		
		addTupla(tupla);
	}
	
	public void removeTupla(Tupla tupla){
		dados.RemoveTupla(tupla);
		this.getHeader().decBytes(tupla.getSize());
		
		if(dados.isEmpty() && events != null){
			events.blocoVazio(this);
		}else if(events != null){
			events.blocoAlterado(this);
		}
	
	}
	
	public void removeTupla(int index){
		int tuplaSize = dados.RemoveTupla(index);
		this.getHeader().decBytes(tuplaSize);
		
		if(dados.isEmpty() && events != null){
			events.blocoVazio(this);
		}else if(events != null){
			events.blocoAlterado(this);
		}
	}

	@Override
	public byte[] getByteArray() throws IncorrectFormatException {
		byte[] retorno = new byte[BlocoControle.TAMANHO_BLOCO];
		
		byte[] content =  ByteArrayTools
				.concatArrays(header.getByteArray(), dados.getByteArray());
		
		ByteArrayTools.appendArrays(retorno, content, 0);
		
		return retorno;		
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		this.header = new HeaderBloco(ByteArrayTools
				.subArray(dados, 0, HEADER_BLOCO_SIZE));
		
		this.dados = new DadosBloco(ByteArrayTools
				.subArray(dados, HEADER_BLOCO_SIZE, dados.length - HEADER_BLOCO_SIZE)
				, descritor);
	}
	
	public int getOffset(Tupla tupla){
//		dados.getOffset(tupla);
		throw new RuntimeException("Não implementado"); 
	}
	
	@Override
	public String toString() {
		String retorno = "";
		byte cId = header.getContainerId();
		int bId = header.getBlocoId();
		
		for(int i = 0; i < dados.size(); i++){
			retorno += "("+cId + " " + bId + " " + i + ") " + dados.getTupla(i).toString() + "\n";
		}
		return retorno;
	}
}
