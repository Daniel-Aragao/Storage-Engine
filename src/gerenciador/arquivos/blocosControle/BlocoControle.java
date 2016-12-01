package gerenciador.arquivos.blocosControle;

import java.beans.EventSetDescriptor;

import gerenciador.arquivos.enums.ETipoBlocoArquivo;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class BlocoControle implements IBinarizable<BlocoControle> {
	public static final int MIN_ARRAY_SIZE = 12, 
			TAMANHO_NOME = 20,
			TAMANHO_INDICES = 11,
			HEADER_CONTROLE_SIZE = MIN_ARRAY_SIZE + TAMANHO_NOME + TAMANHO_INDICES, 
			TAMANHO_BLOCO = 4096, 
			TRES_BYTES = 16777215;
	
	private HeaderControle header;
	private Descritor descritor;
	
	//atualizar tamanho do descritor ao acrescentar colunas
// descritor bloco de controle é representado por nome, tipo e tamanho de cada coluna
// primeira linha será descrição do arquivo
// cada linha do arquivo será uma linha da tabela 
// deve validade se a linha inserida está seguindo os conformes do descritor, 
	//como tipo e tamanho, por exemplo se a coluna id é do tipo int e não string
	
	public BlocoControle(String nome, String[] propriedades, byte containerId, ETipoBlocoArquivo tipo)throws IncorrectFormatException{		
		descritor = new Descritor(propriedades);
		header = new HeaderControle(nome, containerId, descritor.getDescritorSize(), tipo);
	}
	
	public BlocoControle(String nome, UnidadeDescricao[] propriedades, byte containerId, ETipoBlocoArquivo tipo)throws IncorrectFormatException{		
		descritor = new Descritor(propriedades);
		header = new HeaderControle(nome, containerId, descritor.getDescritorSize(), tipo);
	}
	
	public BlocoControle(String nome, Descritor descritor, byte containerId, ETipoBlocoArquivo tipo)throws IncorrectFormatException{		
		this.descritor = descritor;
		header = new HeaderControle(nome, containerId, descritor.getDescritorSize(), tipo);
	}
	
	public BlocoControle(byte[] dados){
		fromByteArray(dados);
	}
	public HeaderControle getHeader() {
		return header;
	}
	public int getProxBlocoLivre(){
		return header.getProxBlocoLivre();
	}

	public Descritor getDescritor() {
		return descritor;
	}
	
	public String addIndice(byte containerId){
		return header.addIndice(containerId);
	}
	
	public void setIndices(byte[]indices){
		header.setIndices(indices);
	}
	
	public byte getIndice(int index){
		return header.getIndice(index);
	}

	@Override
	public byte[] getByteArray() {
		byte[]retorno = new byte[TAMANHO_BLOCO];
		
		byte[] content = ByteArrayTools.concatArrays(
					header.getByteArray(), 
					descritor.getByteArray());
		
		ByteArrayTools.appendArrays(retorno, content, 0);		
		
		return retorno;
	}

	@Override
	public void fromByteArray(byte[] dados) {
		this.header = new HeaderControle(ByteArrayTools
				.subArray(dados, 0, HEADER_CONTROLE_SIZE));
		
		this.descritor = new Descritor(ByteArrayTools
				.subArray(dados, HEADER_CONTROLE_SIZE, 
						dados.length - HEADER_CONTROLE_SIZE), header.getSizeDescritor());		
	}

	public byte[] getIndices() {
		return this.header.getIndices();
	}

	public void setQtdeIndice(byte id) {
		setQtdeIndice(id);
		
	}
}
