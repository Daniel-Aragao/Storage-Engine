package gerenciador.arquivos.blocosControle;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IBinarizable;
import gerenciador.utils.ByteArrayTools;

public class Descritor implements IBinarizable<Descritor>{
	
	private ArrayList<UnidadeDescricao> descs;
	
	public Descritor(String[] descs) throws IncorrectFormatException{
		this.descs = new ArrayList<>();
		
		Pattern p = Pattern.compile(GerenciadorArquivos.REGEX_COLUMN);
		
		for(String d : descs){
			if(d != null && !d.isEmpty()){
				
				Matcher m = p.matcher(d);				
				
				if(m.find()){
					this.descs.add(new UnidadeDescricao(m.group(1),
						ETipoColuna.getByValue(m.group(2).charAt(0)), 
						Byte.parseByte(m.group(3))));				
				}
				
			}
		}
	}
	
	public short getDescritorSize(){
		return (short) (descs.size()*UnidadeDescricao.UNIDADE_DESCRICAO_SIZE);		
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
