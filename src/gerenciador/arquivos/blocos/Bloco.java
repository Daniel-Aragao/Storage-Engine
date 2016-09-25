package gerenciador.arquivos.blocos;

import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.enums.ETipoBloco;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class Bloco implements IBinarizable<Arquivo> {
	public static final int HEADER_BLOCO_SIZE = 8;

	private HeaderBloco header;
	private DadosBloco dados;
	
	private Descritor descritor;

	public Bloco(byte containerId, int BlockId, ETipoBloco tipoBloco, Descritor descritor){
		this.descritor = descritor;
		header = new HeaderBloco(containerId, BlockId, tipoBloco);
		dados = new DadosBloco(descritor);
	}
	
	
	public Bloco(byte[] dados, Descritor descritor) throws IncorrectFormatException {
		this.descritor = descritor;
		fromByteArray(dados);
	}

	public HeaderBloco getHeader() {
		return header;
	}
	
	public DadosBloco getDados() {
		return dados;
	}
	
	public void addTupla(Tupla tupla){
		dados.addTupla(tupla);
		this.getHeader().setBytesUsados(tupla.getSize() + this.getHeader().getBytesUsados());
	}
	
	public void addTupla(byte[] tupla) throws IncorrectFormatException{
		dados.addTupla(tupla);
	}

	@Override
	public byte[] getByteArray() {
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
}
