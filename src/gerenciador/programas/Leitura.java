package gerenciador.programas;

import javax.swing.JOptionPane;

import gerenciador.GerenciadorArquivos;
import gerenciador.arquivos.Arquivo;
import gerenciador.loger.Log;

public class Leitura {

	public static void main(String[] args) {
		String containerId ;
		
		do{
			containerId = JOptionPane.showInputDialog("Id da tabela");
		}while(containerId == null || containerId.isEmpty());

		try{
			GerenciadorArquivos ga = new GerenciadorArquivos();
			Arquivo a = ga.getArquivo(Byte.parseByte(containerId));
			
			System.out.println(ga.getArquivoString(a));
			
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
	}

}
