package gerenciador.TestesArquivo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.ETipoColuna;

public class Arquivo {

	public static void main(String[] args) {
//		byte[] t = "oi ana".getBytes();
//		byte[] t2 = "37".getBytes();
//		// System.out.println("byte: " + (t[5]== t2[3]) +" "+t[5]+" "+t2[3]);
//
//		System.out.println("---");
//
//		System.out.println(new String(t));
//
//		System.out.println("---");
//		System.out.println(t2.length);
//		System.out.println(3 + Integer.parseInt(new String(t2)));
//
//		System.out.println(new String(t2));
//		System.out.println("---");
//
//		int a = 1075841040;//500100; //maior é 127 por byte
//		System.out.println((256<<2)>>2); // continua 256
//		byte b[] = new byte[4];
//		b[0] = (byte) (a >> 24); // pegou o byte mais significativo
//		b[1] = (byte) (a >> 16);
//		b[2] = (byte) (a >> 8);
//		b[3] = (byte) (a >> 0); // pegou o byte menos significativo
//		
//		System.out.println(b[0]);
//		System.out.println(b[1]);
//		System.out.println(b[2]);
//		System.out.println(b[3]);
//		System.out.println("- |"++"| -");
		
		
		String INPUT = "COD_AUTHOR[I(5000)]a";//"This order was placed for QT3000! OK?";//"COD_AUTHOR[I(5)]|NAME_AUTHOR[A(100)]|";
		String REGEX = "([^\\[]+)\\[(.*)\\((.*)\\)\\]";//"([^\\[]+)\\[([^]]+)\\]";
		
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(INPUT);
		
		if (m.find( )) {
			System.out.println(m.groupCount());
	        System.out.println("Found value: " + m.group(0) );
	        System.out.println("Found value: " + m.group(1) );
	        System.out.println("Found value: " + m.group(2) );
	        System.out.println("Found value: " + m.group(3) );
	    } else {
	    	System.out.println("NO MATCH");
	    }
		
		
		
		System.out.println(ETipoColuna.getByValue('I'));
	}

}
