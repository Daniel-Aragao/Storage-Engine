package gerenciador.programas;

import javax.swing.JOptionPane;

import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.interfaces.IArquivo;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.Log;
import gerenciador.loger.LogLeituraTabela;

public class Leitura {

	private static ILog Log;
	public static void main(String[] args) {
		String containerId ;

		do{
			containerId = JOptionPane.showInputDialog("Id da tabela");
			if(containerId == null) return;
		}while(containerId.isEmpty());
		
		
		try{
			GerenciadorArquivos ga = new GerenciadorArquivos();
			IArquivo a = ga.getArquivo(Byte.parseByte(containerId));
			
			Log = new LogLeituraTabela(a.getFile());
//			Log = new Log();
			Log.Write("Nome: " + a.getNome());
			for(int i = 1; i < a.getBlocoControle().getProxBlocoLivre(); i++){
				Log.Write(ga.getBloco(a.getId(), i).toString());				
			}
			
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
	}

}
