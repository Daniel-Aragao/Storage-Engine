package gerenciador.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IO_Operations {

	public static byte[] readFromFile(File file, int start, int length) {
		byte[] retorno = null;
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.seek(start);

			retorno = new byte[length];
			raf.read(retorno);

			raf.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	public static void readFromFile(File file, int offset, byte byteArray[]) {
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.seek(offset);

			raf.read(byteArray);

			raf.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeFile(File file, byte[] byteArray, int offset) {
		try {
			
			RandomAccessFile raf = new RandomAccessFile(file,"rw");
			raf.seek(offset);
			raf.write(byteArray);
			raf.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
