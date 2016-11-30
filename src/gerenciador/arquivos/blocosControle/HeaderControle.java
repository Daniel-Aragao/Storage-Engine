package gerenciador.arquivos.blocosControle;

import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.Log;
import gerenciador.utils.ByteArrayTools;

public class HeaderControle implements IBinarizable<HeaderControle>{
	//HEADER 11b
	
	private byte containerId;
	private int sizeBloco;
	private byte statusContainer;
	private int proxBlocoLivre;
	private short SizeDescritor;
	private String Nome;
	private byte qtdIndices;
	private byte[] indiceIds;

	private ILog Log;

	public HeaderControle(String nome, byte containerId, short descSize) throws IncorrectFormatException {
		this.Log = new Log();
		setContainerId(containerId);
		setSizeBloco(BlocoControle.TAMANHO_BLOCO);
		setStatusContainer((byte) 0);
		setProxBlocoLivre(1);
		setSizeDescritor(descSize);
		setNome(nome);
		this.indiceIds = new byte[BlocoControle.TAMANHO_INDICES-1];
		this.qtdIndices = 0;
	}
	
	public HeaderControle(byte[] dados){
		fromByteArray(dados);
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
		byteArray[1] = size[1];
		byteArray[2] = size[2];
		byteArray[3] = size[3];
		
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
		byteArray[9] = sizeDesc[2];
		byteArray[10] = sizeDesc[3];
		
		ByteArrayTools.appendArrays(
				byteArray, 
				ByteArrayTools.stringToByteArray(this.Nome, BlocoControle.TAMANHO_NOME), 
				11);
		
		byteArray[31] = this.qtdIndices;
		int i = 32;
		for(byte j : this.indiceIds){
			byteArray[i] = j; 
			i++;
		}
		
		return byteArray;
	}

	private void setContainerId(byte containerId) {
		this.containerId = containerId;
	}

	private void setSizeBloco(int sizeBloco) throws IncorrectFormatException {
		if(sizeBloco > BlocoControle.TRES_BYTES){
			Log.Erro("Tamanho do bloco não pode exceder "
					+ "3 bytes("+BlocoControle.TRES_BYTES+")");
			throw new IncorrectFormatException("Tamanho do bloco não pode exceder "
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
	
	public void incProxBlocoLivre() {
		this.proxBlocoLivre++;
	}
	
	public void decProxBlocoLivre() {
		this.proxBlocoLivre--;
	}
	
	public int qtdIndices(){
		return this.qtdIndices;
	}
	
	public byte[] getIndices(){
		return this.indiceIds;
	}

	public void setSizeDescritor(short sizeDescritor) {
		SizeDescritor = sizeDescritor;
	}

	@Override
	public void fromByteArray(byte[] dados) {
		setContainerId(dados[0]);
		try {
			setSizeBloco(ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 1, 3)));
		} catch (IncorrectFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setStatusContainer(dados[4]);
		setProxBlocoLivre(ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 5, 4)));
		setSizeDescritor((short) ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 9, 2)));
		
		setNome(ByteArrayTools.byteArrayToString(ByteArrayTools.subArray(dados, 11, BlocoControle.TAMANHO_NOME)));
		
		this.qtdIndices = dados[31];
		this.indiceIds = ByteArrayTools.subArray(dados, 32, BlocoControle.TAMANHO_INDICES-1);
	}

	public String getNome() {
		return this.Nome;
	}
	public void setNome(String nome) {
		this.Nome = nome;
	}

}
