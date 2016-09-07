package gerenciador.arquivos.blocosControle;

import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.loger.Log;
import gerenciador.utils.ByteArrayTools;

public class HeaderControle implements IBinarizable<HeaderControle>{
	//HEADER 11b
	
	private byte containerId;
	private int sizeBloco;
	private byte statusContainer;
	private int proxBlocoLivre;
	private short SizeDescritor;

	
	public HeaderControle(byte containerId, short descSize) throws IncorrectFormatException {
		setContainerId(containerId);
		setSizeBloco(BlocoControle.TAMANHO_BLOCO);
//		setStatusContainer(0);
		setProxBlocoLivre(1);
		setSizeDescritor(descSize);
	}
	
	public byte getContainerId() {
		return containerId;
	}

	public int getSizeBloco() {
		return sizeBloco;
	}

	public byte getStatusContainer() {
		return statusContainer;
	}

	public int getProxBlocoLivre() {
		return proxBlocoLivre;
	}

	public short getSizeDescritor() {
		return SizeDescritor;
	}

	@Override
	public byte[] getByteArray() {
		byte []byteArray = new byte[BlocoControle.HEADER_CONTROLE_SIZE];
		
		// byte 0 - id do container
		byteArray[0] = containerId;
		
		// byte 123 - size dos blocos = 4096b
		byte[] size = ByteArrayTools.intToByteArray(sizeBloco);
		byteArray[1] = size[0];
		byteArray[2] = size[1];
		byteArray[3] = size[2];
		
		// byte 4 - status do container
		byteArray[4] = statusContainer;
		
		// byte 5, 6, 7, 8 - indica id do proximo bloco livre
		byte[] nextBloco = ByteArrayTools.intToByteArray(proxBlocoLivre);
		byteArray[5] = nextBloco[0];
		byteArray[6] = nextBloco[1];
		byteArray[7] = nextBloco[2];
		byteArray[8] = nextBloco[3];
		
		// byte 9, 10 - tamanho do header do container (body do bloco de controle)
		byte[] sizeDesc = ByteArrayTools.intToByteArray(SizeDescritor);
		byteArray[9] = sizeDesc[0];
		byteArray[10] = sizeDesc[1];
		
		return byteArray;
	}

	private void setContainerId(byte containerId) {
		this.containerId = containerId;
	}

	private void setSizeBloco(int sizeBloco) throws IncorrectFormatException {
		if(sizeBloco > BlocoControle.TRES_BYTES){
			Log.Erro("Tamanho do bloco n�o pode exceder "
					+ "3 bytes("+BlocoControle.TRES_BYTES+")");
			throw new IncorrectFormatException("Tamanho do bloco n�o pode exceder "
					+ "3 bytes("+BlocoControle.TRES_BYTES+")");	
		}
		this.sizeBloco = sizeBloco;			
	}

	public void setStatusContainer(byte statusContainer) {
		this.statusContainer = statusContainer;
	}

	public void setProxBlocoLivre(int proxBlocoLivre) {
		this.proxBlocoLivre = proxBlocoLivre;
	}

	public void setSizeDescritor(short sizeDescritor) {
		SizeDescritor = sizeDescritor;
	}

}
