package gerenciador.programas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import gerenciador.GerenciadorBuffer;
import gerenciador.RowId;
import gerenciador.arquivos.blocosControle.BlocoControle;
import gerenciador.arquivos.interfaces.ILog;
import gerenciador.loger.Log;
import gerenciador.loger.LogLeituraTabela;

public class ListarRowID {
	private static ILog Log;
	public static final File path = new File(
//			"C:\\Users\\Pedro\\Documents\\GitHub\\Storage-Engine\\res\\Log\\listaID.txt");
			"C:\\Users\\danda_000\\git\\Storage-Engine\\res\\Log\\listaID.txt");

	public static void main(String[] args) {
//		Log = new Log();
		Log = new LogLeituraTabela(
				new File("C:\\Users\\danda_000\\git\\Storage-Engine\\res\\Log\\bufferResult.txt"));
		
		// final String arquivo = "listaID.txt";
		GerenciadorBuffer bm = new GerenciadorBuffer(Log);

		// public ArrayList<RowId> listar[] {
		ArrayList<RowId> listaID = new ArrayList<RowId>();

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);

			String linha;
			while ((linha = br.readLine()) != null) {
//				Log.Write(linha);
//				Log.Write("Splitting no row id");

				String[] dados = linha.split("\\.");
				
				RowId Ri = new RowId(Byte.parseByte(dados[0]), Integer.parseInt(dados[1]),
						Integer.parseInt(dados[2]));
				
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
		
		for (RowId id : listaID) {
			bm.getBloco(id);
		}
		Log.Write("Hit: "+bm.getHit()+" = "+bm.getHit()/bm.getAcessos());Log.Write(System.lineSeparator());
		Log.Write("Miss: "+bm.getMiss()+" = "+bm.getMiss()/bm.getAcessos());Log.Write(System.lineSeparator());
		Log.Write("Acessos: "+bm.getAcessos()+" = "+1);Log.Write(System.lineSeparator());
		Log.Write("SwapOut: "+bm.getSwaps()+" = "+bm.getSwaps()/bm.getMiss());Log.Write(System.lineSeparator());
		Log.Write("Tamanho da memória em bytes: " + bm.getMemoriaSize());Log.Write(System.lineSeparator());
		Log.Write("Tamanho da memória em blocos: " + bm.getMemoriaSize()/BlocoControle.TAMANHO_BLOCO);Log.Write(System.lineSeparator());
		bm.resetHitMissSwaps();
	}

}
