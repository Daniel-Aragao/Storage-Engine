package gerenciador.arquivos.blocosControle;

import java.util.ArrayList;

import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class Descritor implements IBinarizable<Descritor>{
	
	private ArrayList<UnidadeDescricao> descs;
	
	public Descritor() {
		descs = new ArrayList<>();
	}
	
	public int getDescritorSize(){
		return descs.size()*UnidadeDescricao.UNIDADE_DESCRICAO_SIZE;
	}
	
	@Override
	public byte[] getByteArray() {
		byte[] retorno = new byte[getDescritorSize()];
		
		for(UnidadeDescricao desc : descs){
			ByteArrayTools.appendArrays(retorno, desc.getByteArray());
		}
		
		return retorno;
	}
}
