package gerenciador.utils;

public class ByteArrayTools {

	public static byte[] subArray(byte[] objarray, int size) {
		return subArray(objarray, 0, size);
	}
	
	public static byte[] subArray(byte[] objarray,int startIndex, int size) {
		byte[] array = new byte[size];
		
		for(int j = startIndex; j < size; j++){
			array[j] = objarray[j];
		}
		
		return array;
	}
	
	public static byte[] intToByteArray(int a){
		byte[] retorno = new byte[4];
				
		retorno[0] = (byte) (a >> 24); // pegou o byte mais significativo
		retorno[1] = (byte) (a >> 16);
		retorno[2] = (byte) (a >> 8);
		retorno[3] = (byte) (a >> 0); // pegou o byte menos significativo
		
		return retorno;
	}

}
