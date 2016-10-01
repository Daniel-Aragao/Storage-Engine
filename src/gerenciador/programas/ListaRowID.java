package gerenciador.programas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import gerenciador.GerenciadorBuffer;
import gerenciador.RowId;

public class ListaRowID {

	public static void main(String[] args) {
		final String arquivo = "listaID.txt";
		GerenciadorBuffer bm = new GerenciadorBuffer();

		// public ArrayList<RowId> listar[] {
		ArrayList<RowId> listaID = new ArrayList<RowId>();

		FileReader fr = null;
		BufferedReader br = null;
		

		try {
			fr = new FileReader(arquivo);
			br = new BufferedReader(fr);

			String linha;
			while ((linha = br.readLine()) != null) {
				String[] dados = linha.split(".");
				RowId Ri = new RowId(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]),
						Integer.parseInt(dados[2]));
//				dado do container no rowid tipo byte
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
//colocar os log.write no getbloco
		}

	}

}
