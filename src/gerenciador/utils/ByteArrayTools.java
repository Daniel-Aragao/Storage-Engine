package gerenciador.utils;

import java.io.UnsupportedEncodingException;
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
	
	public static int byteArrayToInt(byte[] bytes){
			// move os 24 bytes mais singificativos para a esquerda.
			//depois compara o signed byte com os bytes 255 para que remover o 
			//sinal e depois move os bits 16 bits para esquerda
		if(bytes.length == 4){
			return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);			
		}else if(bytes.length == 3){
			return bytes[0] << 16 | (bytes[1] & 0xFF) << 8 | (bytes[2] & 0xFF);
		}else if(bytes.length == 2){
			return bytes[0] << 8 | (bytes[1] & 0xFF) ;
		}else{
			return bytes[0] & 0xFF;
		}
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
		byte[] sba = stringToByteArray(string);
		
		int offset = length - sba.length;
		int copylength = sba.length; 
		
		if(offset < 0){
			offset = 0;
			copylength = length;
		}
		
		System.arraycopy(sba, 0, retorno, offset, copylength);		
		
		return retorno;
	}
	
	public static byte[] stringToByteArray(String string){
		return string.getBytes(StandardCharsets.UTF_16BE);
	}
	
	public static String byteArrayToString(byte[] array){
		String retorno = "";
		/*
		0xFF =	0000 0000 - 0000 0000 - 0000 0000 - 1111 1111 AND
		-25 = 	1111 1111 - 1111 1111 - 1111 1111 - 1110 0111 RESULTA EM
		231 = 	0000 0000 - 0000 0000 - 0000 0000 - 1110 0111
		 */
		
		for(int i = 0; i < array.length - 1; i+=2){
			if(array[i] != 0 || array[i+1] != 0){
				retorno += (char)( ((array[i] & 0xFF) << 8 | array[i+1]) & 0xFF);
			}
		}
		
		return retorno;
	}

}
