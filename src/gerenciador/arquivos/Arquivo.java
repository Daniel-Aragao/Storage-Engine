package gerenciador.arquivos;

import java.util.ArrayList;

import gerenciador.arquivos.blocos.Bloco;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class Arquivo implements IBinarizable<Arquivo>{
	private BlocoControle blocoControle;
	private ArrayList<Bloco> blocos;
	private Descritor descritor;
	
	public Arquivo(BlocoControle blocoControle){
		
		this.blocoControle = blocoControle;
		
		blocos = new ArrayList<Bloco>();
		
	}
	
	public Arquivo(byte[] dados) throws IncorrectFormatException {
		blocos = new ArrayList<Bloco>();
		fromByteArray(dados);
	}
	
	public void addBloco(Bloco bloco){
		this.blocos.add(bloco);
		this.blocoControle.getHeader().incProxBlocoLivre();
	}
	
	
	@Override
	public byte[] getByteArray() {
		byte[] blocosArray = blocoControle.getByteArray();
		
		for(Bloco bloco : blocos){
			blocosArray = ByteArrayTools.concatArrays(blocosArray, bloco.getByteArray());
		}
		
		return blocosArray;
	}

	@Override
	public void fromByteArray(byte[] dados) throws IncorrectFormatException {
		// TODO Auto-generated method stub
		
	}
}
