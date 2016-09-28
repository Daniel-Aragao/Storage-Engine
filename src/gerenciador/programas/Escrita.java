package gerenciador.programas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.Arquivo;

public class Escrita {

	public static void main(String[] args) {
//		throw new RuntimeException("Não implementado");
		
		GerenciadorArquivos gerenciadorArquivo = new GerenciadorArquivos();
		
		String path ;
		
		do{
			path = JOptionPane.showInputDialog("Diretório do arquivo");
		}while(path == null || path.isEmpty());
		
		FileReader reader = null;
		try {
			reader = new FileReader(path);
			BufferedReader buffer = new BufferedReader(reader);
			
			String linha = buffer.readLine();
			Arquivo a = gerenciadorArquivo.CriarArquivo(linha);
			
			if(a != null){
				byte containerId = a.getBlocoControle().getHeader().getContainerId();
				
				while((linha = buffer.readLine()) != null){
					if(!linha.isEmpty())
						gerenciadorArquivo.AdicionarLinha(containerId, linha);
				}				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
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
