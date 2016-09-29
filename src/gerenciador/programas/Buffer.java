package gerenciador.programas;

import javax.swing.JOptionPane;

import gerenciador.GerenciadorArquivos;
import gerenciador.GerenciadorBuffer;
import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.Log;

public class Buffer {
	private static ILog Log;
	public static void main(String[] args) {

		String containerId ;

		do{
			containerId = JOptionPane.showInputDialog("Id da tabela");
			if(containerId == null) return;
		}while(containerId.isEmpty());
		
		
		try{
			GerenciadorBuffer gb = new GerenciadorBuffer();
			GerenciadorArquivos ga = new GerenciadorArquivos();
			Arquivo a = ga.getArquivo(Byte.parseByte(containerId));
			
//			Log = new LogLeituraTabela(a.getFile());
			Log = new Log();
			Log.Write("Importando todos os blocos");
			for(int i = 1; i < a.getBlocoControle().getProxBlocoLivre(); i++){
//				Log.Write(ga.getBloco(a.getId(), i).toString());		
				Log.Write("Bloco..." + ga.getBloco(a.getId(), i).getBlocoId()+"...Importado");
//				gb.getBloco(ga.getBloco(a.getId(), i).getBlocoTupleId());
			}
			Log.Write("Blocos importados");
			
			
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		
	}
}
