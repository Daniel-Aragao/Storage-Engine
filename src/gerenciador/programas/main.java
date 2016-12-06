package gerenciador.programas;

import java.io.IOException;
import java.util.ArrayList;

import gerenciador.GerenciadorArquivos;
import gerenciador.GerenciadorIndice;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.blocosControle.UnidadeDescricao;
import gerenciador.arquivos.enums.ETipoColuna;
import gerenciador.arquivos.exceptions.IncorrectFormatException;
import gerenciador.arquivos.interfaces.IArquivo;
import gerenciador.arquivos.interfaces.ILog;

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
		
		
		
		GerenciadorArquivos ga = new GerenciadorArquivos(new ILog() {
			
			@Override
			public void Write(String msg) {
				
				
			}
			
			@Override
			public void Erro(String msg) {
				
				
			}
		});
		GerenciadorIndice gi = new GerenciadorIndice();
		
		int tabela = 6;
		IArquivo a = ga.getArquivo((byte) tabela);
		limparIndices(a, ga);
//		UnidadeDescricao[] ud = new UnidadeDescricao[1];
//		ud[0] = new UnidadeDescricao("COD_AUTHOR", ETipoColuna.inteiro, (byte) 5);
////		ud[1] = new UnidadeDescricao("NAME_AUTHOR", ETipoColuna.string, (byte) 100);
////		ud[0] = new UnidadeDescricao("COD_FORN", ETipoColuna.inteiro, (byte) 5);
////		ud[1] = new UnidadeDescricao("NOME_FORN", ETipoColuna.string, (byte) 25);
//		
//		System.out.println("Ordem: " + gi.CriarIndice((byte) tabela, ud, a.getNome()+ " índice"));
//		
//		
//		String[] chave = {"101"};
//		System.out.println(gi.Buscar(chave, (byte) 9));	 // 7, 8	
		
//		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
//		printgalera(ga);
//		System.out.println(ga.getArquivo((byte) 8));
	}
	
	private static void printgalera(GerenciadorArquivos ga){
		ArrayList<IArquivo> arquivos = ga.getTabelas();
		for(IArquivo a : arquivos){
			System.out.println(a.getNome() + " : "+a.getTipo());
			for (UnidadeDescricao c : a.getDescritor().getDescritores()){
				System.out.println("   "+c.toString());				
			}
			System.out.println();
		}
	}
	
	private static void limparIndices(IArquivo a, GerenciadorArquivos ga){
		a.getBlocoControle().setQtdeIndice((byte) 0);
		a.getBlocoControle().setIndices(new byte[BlocoControle.TAMANHO_INDICES-1]);
		a.atualizar();
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
