package gerenciador.programas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import gerenciador.GerenciadorBuffer;
import gerenciador.RowId;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.Log;

public class ListaRowID {
	private static ILog Log;
	public static final File path = new File(
			"C:\\Users\\Pedro\\Documents\\GitHub\\Storage-Engine\\res\\Log\\listaID.txt");

	public static void main(String[] args) {
		Log = new Log();
		
		// final String arquivo = "listaID.txt";
		GerenciadorBuffer bm = new GerenciadorBuffer();

		// public ArrayList<RowId> listar[] {
		ArrayList<RowId> listaID = new ArrayList<RowId>();

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);

			String linha;
			while ((linha = br.readLine()) != null) {
				Log.Write(linha);
				Log.Write("Splitting a tupla");

				String[] dados = linha.split("\\.");
				
				RowId Ri = new RowId(Byte.parseByte(dados[0]), Integer.parseInt(dados[1]),
						Integer.parseInt(dados[2]));
				// dado do container no rowid tipo byte
				listaID.add(Ri);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// return listaID;
		// }

		for (RowId id : listaID) {

			bm.getBloco(id);
			
			// colocar os log.write no getbloco
		}
		Log.Write("Hit:"+bm.getHit());
		Log.Write("Miss:"+bm.getMiss());
		Log.Write("Acessos:"+bm.getAcessos());
		bm.resetHitNhit();
	}

}
