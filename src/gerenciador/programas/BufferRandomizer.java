package gerenciador.programas;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import gerenciador.GerenciadorArquivos;
import gerenciador.RowId;
import gerenciador.arquivos.Arquivo;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.Log;

public class BufferRandomizer {
	private static ILog Log;

	public static void main(String[] args) {

		ArrayList<RowId> ids;
		String containerId;
		Log = new Log();
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
			Arquivo a = ga.getArquivo(Byte.parseByte(containerId));

			// Log = new LogLeituraTabela(a.getFile());

			Log.Write("Verificando número de blocos");
			System.out.println(a.getBlocoControle().getProxBlocoLivre() - 1);

			Log.Write("Calculando 40% dos blocos");
			four = 0.4f;
			forty = (int) ((a.getBlocoControle().getProxBlocoLivre() - 1) * four);
			System.out.println(forty);

			Log.Write("Importando os blocos");
			for (int i = 1; i < forty; i++) {

				// System.out.println(a.getBlocoControle().getProxBlocoLivre());
				// Log.Write(ga.getBloco(a.getId(), i).toString());

				Log.Write("Bloco..." + ga.getBloco(a.getId(), i).getBlocoId() + "...Importado");

				Log.Write("Importando as tuplas");
				Log.Write("\n");
				
				for (int j = 0; j < ga.getBloco(a.getId(), i).getDados().getSize(); j++) {
					
					Log.Write("Tupla..." + ga.getBloco(a.getId(), i).getDados().getTupla(j).getTupleId()
							+ "...Importado");
					
					ids.add(ga.getBloco(a.getId(), i).getDados().getTupla(j).getTupleId());
				}
				// gb.getBloco(ga.getBloco(a.getId(), i).getBlocoTupleId());
			}
			Log.Write("Blocos importados");
			Log.Write("Tuplas importadas");
			

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		Collections.shuffle(ids);

		}

	}

