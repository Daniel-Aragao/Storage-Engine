package gerenciador.arquivos.blocos;

import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class ColunaString extends Coluna{

	private String dado;
	
	public ColunaString(String prop, UnidadeDescricao descricao) throws IncorrectFormatException{
		super((short) (prop.length()*2));
		if (descricao.getTipo() != ETipoColuna.string){
			throw new IncorrectFormatException("Descritor não possui o tipo String");
		}
		
		dado = prop;
		
	}
	
	public ColunaString(byte[] dados){
		super((short) ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 2)));
		fromByteArray(dados);
	}

	@Override
	public byte[] getByteArray() {
		byte[] retorno = new byte[2 + this.getColumnSize()];
		
		ByteArrayTools.appendArrays(retorno,
				ByteArrayTools.intToByteArray(this.getColumnSize()), 
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

}
