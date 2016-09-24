package gerenciador.arquivos.blocos;

import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.utils.ByteArrayTools;

public class ColunaInt extends Coluna{
	
	private int dado;

	public ColunaInt(String prop, UnidadeDescricao descricao) throws IncorrectFormatException{
		super((short) 4);
		if (descricao.getTipo() != ETipoColuna.inteiro){
			throw new IncorrectFormatException("Descritor não possui o tipo inteiro");
		}
		
		try{
			dado = Integer.parseInt(prop);
		}catch(NumberFormatException e){
			throw new IncorrectFormatException("Tipo esperado para "+prop+" é inteiro");			
		}
	}
	
	public ColunaInt(byte[] dados) {
		super((short) 4);
		fromByteArray(dados);
	}

	@Override
	public byte[] getByteArray() {
		// 2 do size e 4 do dado
		byte[] retorno = new byte[2 + 4];
		
		ByteArrayTools.appendArrays(retorno,
				ByteArrayTools.intToByteArray(this.getColumnSize()), 
				0
		);
		ByteArrayTools.appendArrays(retorno,
				ByteArrayTools.intToByteArray(this.dado), 
				2
		);		
		
		return retorno;
	}

	@Override
	public void fromByteArray(byte[] dados) {
		ByteArrayTools.byteArrayToInt(ByteArrayTools.subArray(dados, 2, 4));
		
	}

}
