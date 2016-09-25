package gerenciador.arquivos.blocos;

import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class ColunaString extends Coluna{

	private String dado;
	
	public ColunaString(String prop, UnidadeDescricao descricao) throws IncorrectFormatException{
		super((short) (prop.length()*2 + 2));
		if (descricao.getTipo() != ETipoColuna.string){
			throw new IncorrectFormatException("Descritor não possui o tipo String");
		}
		
		if((prop.length()) > descricao.getTamanho()){
			throw new IncorrectFormatException("Tipo esperado para "
					+prop+" é String( "+descricao.getTamanho()+" )");			
		}
		
		dado = prop;
		
	}
	
	public ColunaString(byte[] dados){
		super((short) ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 2)));
		fromByteArray(dados);
	}

	@Override
	public byte[] getByteArray() {
		byte[] retorno = new byte[this.getColumnSize()];
		
		ByteArrayTools.appendArrays(retorno,
				ByteArrayTools.subArray(ByteArrayTools.intToByteArray(this.getColumnSize()),2,2), 
				0
		);
		ByteArrayTools.appendArrays(retorno,
				ByteArrayTools.stringToByteArray(this.dado), 
				2
		);		
		
		return retorno;
		
	}

	@Override
	public void fromByteArray(byte[] dados) {
		this.dado = 
				ByteArrayTools.byteArrayToString(
						ByteArrayTools.subArray(dados, 2, dados.length - 2)
				);
		
	}

	@Override
	public String getDado() {
		return dado;
	}

}
