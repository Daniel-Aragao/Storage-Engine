package gerenciador.buffer;

import gerenciador.RowId;
import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.buffer.interfaces.IMemoryEvents;
import gerenciador.utils.ByteArrayTools;

public class Memoria {
	public static final int MEMORY_SIZE_IN_BLOCKS = 4000;
	public static final int MEMORY_SIZE_IN_BYTES = MEMORY_SIZE_IN_BLOCKS * BlocoControle.TAMANHO_BLOCO;
	private byte[] blocos;
	private IMemoryEvents events;
	
	public Memoria(IMemoryEvents events){
		this.events = events;
		blocos = new byte[MEMORY_SIZE_IN_BYTES];
	}
	
	public boolean contains(IBloco bloco){
		throw new RuntimeException("Não implementado");
	}
	
	public boolean contains(RowId tid){
		
		for(int i = 0; i < blocos.length; i += BlocoControle.TAMANHO_BLOCO){
			RowId eachTid = new RowId(
					blocos[i], 
					ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(blocos, i+1, 3)),
					0
			);
			
			if(tid.isSameBloco(eachTid)){
				return true;
			}
		}
		return false;
	}

	public IBloco getBloco(RowId tid) {
		
		for(int i = 0; i < blocos.length; i += BlocoControle.TAMANHO_BLOCO){
			RowId eachTid = new RowId(
					blocos[i], 
					ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(blocos, i+1, 3)),
					0
			);
			
			if(tid.isSameBloco(eachTid)){
				try {
					return new Bloco(
							
							ByteArrayTools.subArray(blocos, i, BlocoControle.TAMANHO_BLOCO), 
								events.requisitarDescritor(tid.getContainerId()));
					
				} catch (IncorrectFormatException e) {

					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	public IBloco getBloco(int index){
		int i = index * BlocoControle.TAMANHO_BLOCO;
		try {
			
			return new Bloco(
					ByteArrayTools.subArray(blocos, i, BlocoControle.TAMANHO_BLOCO), 
						events.requisitarDescritor(blocos[i]));
			
		} catch (IncorrectFormatException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	public int getPosition(RowId tid){
		for(int i = 0; i < blocos.length; i += BlocoControle.TAMANHO_BLOCO){
			RowId eachTid = new RowId(
					blocos[i], 
					ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(blocos, i+1, 3)),
					0
			);
			
			if(tid.isSameBloco(eachTid)){
				return i / BlocoControle.TAMANHO_BLOCO;
			}
		}
		return -1;
	}
	
	public void putBloco(Bloco b, int posMem){
		try {			
			ByteArrayTools.appendArrays(blocos, b.getByteArray(), BlocoControle.TAMANHO_BLOCO*posMem);
			
		} catch (IncorrectFormatException e) {
			e.printStackTrace();
		}
	}
	
	public int getPosVazia(){
		for(int i = 0; i < blocos.length; i += BlocoControle.TAMANHO_BLOCO){
			if(blocos[i] == 0) return i / BlocoControle.TAMANHO_BLOCO;
		}
		return -1;
	}

	public int getSize() {
		return blocos.length;
	}
}
