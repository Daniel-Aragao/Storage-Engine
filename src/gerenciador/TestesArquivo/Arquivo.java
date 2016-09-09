package gerenciador.TestesArquivo;

import java.io.IOException;

import gerenciador.GerenciadorArquivos;

public class Arquivo {

	public static void main(String[] args) throws IOException {
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
		
		
//		String INPUT = "NAME_AUTHOR[A(100)]|";//"This order was placed for QT3000! OK?";//"COD_AUTHOR[I(5)]|NAME_AUTHOR[A(100)]|";
//		String REGEX = "([^\\[]+)\\[(.*)\\((.*)\\)\\]";//"([^\\[]+)\\[([^]]+)\\]";
//		
//		Pattern p = Pattern.compile(REGEX);
//		Matcher m = p.matcher(INPUT);
//		
//		if (m.find( )) {
//			System.out.println(m.groupCount());
//	        System.out.println("Found value: " + m.group(0) );
//	        System.out.println("Found value: " + m.group(1) );
//	        System.out.println("Found value: " + ETipoColuna.getByValue(m.group(2).charAt(0)) );
//	        System.out.println("Found value: " + Byte.parseByte(m.group(3)) );
//	    } else {
//	    	System.out.println("NO MATCH");
//	    }
//		
//		
//		
//		System.out.println(ETipoColuna.getByValue('I'));
		
//		System.out.println(new File(GerenciadorArquivos.DISC_PATH.getAbsolutePath() + "\\teste.txt").createNewFile());
//		System.out.println(GerenciadorArquivos.DISC_PATH.getPath());
		
		
		new GerenciadorArquivos().CriarArquivo("COD_AUTHOR[I(5)]|NAME_AUTHOR[A(100)]|");
		
	}

}
