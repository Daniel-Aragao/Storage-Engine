package gerenciador.programas;

import javax.swing.JOptionPane;

import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.Log;
import gerenciador.loger.LogArquivo;

public class Leitura {

	private static ILog Log;
	public static void main(String[] args) {
		String containerId ;
//		Log = new LogArquivo();
		Log = new Log();

		do{
			containerId = JOptionPane.showInputDialog("Id da tabela");
		}while(containerId == null || containerId.isEmpty());

		try{
			GerenciadorArquivos ga = new GerenciadorArquivos();
			Arquivo a = ga.getArquivo(Byte.parseByte(containerId));
			
			for(int i = 1; i < a.getBlocoControle().getProxBlocoLivre(); i++){
				Log.Write(ga.getBloco(a.getId(), i).toString());				
			}
			
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
	}

}
