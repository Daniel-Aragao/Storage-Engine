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
			
			buffer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
