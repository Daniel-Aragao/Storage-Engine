package gerenciador.programas;

import java.io.IOException;
import java.util.ArrayList;

import gerenciador.GerenciadorArquivos;
import gerenciador.RowId;
import gerenciador.arquivos.blocos.Tupla;
import gerenciador.arquivos.blocosControle.Descritor;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IArquivo;
import gerenciador.arquivos.interfaces.ITupla;

public class main {

	public static void main(String[] args) throws IOException, IncorrectFormatException {
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
		
//		GerenciadorArquivos gerenciadorArquivo = new GerenciadorArquivos();
//		byte containerId = gerenciadorArquivo.CriarArquivo("COD_AUTHOR[I(5)]|NAME_AUTHOR[A(100)]|");
//		
//		gerenciadorArquivo.AdicionarLinha(containerId, "101|LUIZ RUFFATO|");
//		gerenciadorArquivo.AdicionarLinha(containerId, "20|JORGE AMADO|");
//		byte a = 0x00;
//		byte b = 0x6F;
//		System.out.println((char)(a + b));
//		System.out.println(new Integer(2).toString());
		
		GerenciadorArquivos ga = new GerenciadorArquivos();
		ArrayList<IArquivo> arquivos = ga.getTabelas();
		for(IArquivo a : arquivos){
			System.out.println(a.getNome() + " : "+a.getTipo());
			for (UnidadeDescricao c : a.getDescritor().getDescritores()){
				System.out.println("	"+c.toString());				
			}
			System.out.println();
		}
	}
//	
//	private static ITupla getTupla() throws IncorrectFormatException{
//		Descritor d = getDescritor();
//		
//		String props[] = {"101", "LUIZ_RUFFATO"};
//		RowId t = new RowId((byte) 0, 0, 0); 
//		return new Tupla(props, t, d);
//	}
//	
//	private static Descritor getDescritor() throws IncorrectFormatException{
//		String descs[] = {"COD_AUTHOR[I(5)]","NAME_AUTHOR[A(100)]"};
//		return new Descritor(descs); 
//	}

}
