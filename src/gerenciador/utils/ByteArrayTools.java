package gerenciador.utils;

import java.nio.charset.StandardCharsets;

public class ByteArrayTools {
	
	private ByteArrayTools() {
		
	}

	public static byte[] subArray(byte[] objarray, int size) {
		return subArray(objarray, 0, size);
	}
	
	public static byte[] subArray(byte[] objarray,int startIndex, int size) {
		byte[] array = new byte[size];
		
		for(int i = 0, j = startIndex; i < size; j++, i++){
			array[i] = objarray[j];
		}
		
		return array;
	}
	
	public static byte[] intToByteArray(int a){
		byte[] retorno = new byte[4];
				
		retorno[0] = (byte) (a >> 24); // pegou o bYte mais significativo
		retorno[1] = (byte) (a >> 16);
		retorno[2] = (byte) (a >> 8);
		retorno[3] = (byte) (a >> 0); // pegou o bYte menos significativo
		
		return retorno;
	}

	public static byte[] concatArrays(byte[] a, byte[] b) {
		int alen = a.length;
		int blen = b.length;
		byte[] novoArray = new byte[alen + blen];
		
		System.arraycopy(a, 0, novoArray, 0, alen);
		System.arraycopy(b, 0, novoArray, alen, blen);
		
		return novoArray;		
	}
	
	public static void appendArrays(byte[] target, byte[] newData, int offset){
//		int targetlen = target.length;
		int newDatalen = newData.length;
		
		System.arraycopy(newData, 0, target, offset, newDatalen);
	}
	
	public static byte[] stringToByteArray(String string, int length){
		byte[] retorno = new byte[length];
		byte[] sba = string.getBytes(StandardCharsets.UTF_16LE);
		
		int offset = length - sba.length;
		int copylength = sba.length; 
		
		if(offset < 0){
			offset = 0;
			copylength = length;
		}
		
		System.arraycopy(sba, 0, retorno, offset, copylength);		
		
		return retorno;
	}

}
