package gerenciador.programas;

import javax.swing.JOptionPane;

import gerenciador.GerenciadorArquivos;

public class Escrita {

	public static void main(String[] args) {
//		throw new RuntimeException("Não implementado");
		
		GerenciadorArquivos gerenciadorArquivo = new GerenciadorArquivos();
		
		String path = JOptionPane.showInputDialog("Diretório do arquivo");
		
		
//		gerenciadorArquivo.CriarArquivo("COD_AUTHOR[I(5)]|NAME_AUTHOR[A(100)]|");
		
//		BlocoControle bc = gerenciadorArquivo.getBlocoControle((byte)0);
//		HeaderControle header = bc.getHeader();
//		UnidadeDescricao ud1 = bc.getDescritor().getUnidadeDescricao(0);
//		UnidadeDescricao ud2 = bc.getDescritor().getUnidadeDescricao(1);
//		
//		System.out.println(ud1.getNome()+" / "+ ud1.getTamanho()+" / "+ ud1.getTipo());
//		System.out.println(ud2.getNome()+" / "+ ud2.getTamanho()+" / "+ ud2.getTipo());
//		System.out.println(header.getProxBlocoLivre());
	}

}
