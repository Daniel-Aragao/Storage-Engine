package gerenciador.programas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.interfaces.IArquivo;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.LogEscrita;

public class Escrita {

	public static void main(String[] args) {
		
		ILog log = new LogEscrita(new File(ILog.LOG_PATH.getAbsolutePath() + "\\Log de escrita"));
		GerenciadorArquivos gerenciadorArquivo = new GerenciadorArquivos(log);
		log.Write("Início de escrita\n\n");
		String path ;
		
		do{
			path = JOptionPane.showInputDialog("Diretório do arquivo");
			if(path == null) return;
		}while(path.isEmpty());
		
		FileReader reader = null;
		try {
			reader = new FileReader(path);
			BufferedReader buffer = new BufferedReader(reader);
			
			String linha = buffer.readLine();
			IArquivo a = gerenciadorArquivo.CriarArquivo(linha);
			
			if(a != null){
				byte containerId = a.getBlocoControle().getHeader().getContainerId();
				
				while((linha = buffer.readLine()) != null){
					if(!linha.isEmpty())
						gerenciadorArquivo.AdicionarLinha(containerId, linha);
				}				
			}
			
			buffer.close();
			log.Write("Fim de escrita\n\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
