package gerenciador.loger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import gerenciador.arquivos.interfaces.ILog;

public class LogArquivo implements ILog {
	private FileWriter fw;
	
	public LogArquivo(File f) {
		String path = Log.LOG_PATH.getAbsolutePath() +"\\" + f.getName();
		try {
			this.fw = new FileWriter(path, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void Write(String msg) {
		try {
			fw.write(msg);
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}

	@Override
	public void Erro(String msg) {
		throw new RuntimeException("Não implementado");

	}

}
