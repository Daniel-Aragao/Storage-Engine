package gerenciador.programas;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import gerenciador.GerenciadorArquivos;
import gerenciador.RowId;
import gerenciador.arquivos.blocos.IDados;
import gerenciador.arquivos.interfaces.IArquivo;
import gerenciador.arquivos.interfaces.IBloco;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.Log;
import gerenciador.loger.LogLeituraTabela;

public class BufferRandomizer {
	private static ILog Log;
	private static LogLeituraTabela LogFile;
	public static final File Disc =
	// new
	// File("C:\\Users\\pedro\\Documents\\GitHub\\Storage-Engine\\res\\Disco\\listaID.txt");
	new File("C:\\Users\\danda_000\\git\\Storage-Engine\\res\\Disco\\listaID.txt");

	public static void main(String[] args) {

		ArrayList<RowId> ids = new ArrayList<RowId>();
		String containerId;
		Log = new Log();
		LogFile = new LogLeituraTabela(Disc);
		int forty;
		float four;

		do {
			containerId = JOptionPane.showInputDialog("Id da tabela");
			if (containerId == null)
				return;
		} while (containerId.isEmpty());

		try {
			// GerenciadorBuffer gb = new GerenciadorBuffer();
			GerenciadorArquivos ga = new GerenciadorArquivos();
			IArquivo a = ga.getArquivo(Byte.parseByte(containerId));
			@SuppressWarnings("unused")
			Random random = new Random();

			// Log = new LogLeituraTabela(a.getFile());

			Log.Write("Verificando número de blocos");
			Log.Write("\n");
			Log.Write("" + (a.getBlocoControle().getProxBlocoLivre() - 1));

			Log.Write("Calculando 40% dos blocos");
			four = 0.4f;
			forty = (int) ((a.getBlocoControle().getProxBlocoLivre() - 1) * four);
			Log.Write("" + forty);

			Log.Write("Importando os blocos");
			for (int i = 1; i < forty; i++) {

				// System.out.println(a.getBlocoControle().getProxBlocoLivre());
				// Log.Write(ga.getBloco(a.getId(), i).toString());
				IBloco b = ga.getBloco(a.getId(), i);
				IDados dados = b.getDados();
				
				Log.Write("Bloco..." + b.getBlocoId() + "...Importado");

				Log.Write("Importando as tuplas");
				Log.Write("\n");

				for (int j = 0; j < dados.getSize(); j++) {
					Log.Write("Tupla..." + dados.getTupla(j).getTupleId() + "...Importada");
					ids.add(dados.getTupla(j).getTupleId());
					
//					if(j > 3 && j % 2 == 1){
//						if(random.nextBoolean()){
//							j -= 3;
//						}
//					}else if(j > 1 && j % 2 == 0){
//						if(random.nextBoolean()){
//							j--;
//						}
//					}
					

				}
				Log.Write("Tupla salva em lista");
			}
			Log.Write("Blocos importados");
			Log.Write("Tuplas importadas");

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		Collections.shuffle(ids);
		for (RowId r : ids) {
			LogFile.Write(r.toString());
			LogFile.Write(System.lineSeparator());
		}

	}

}
