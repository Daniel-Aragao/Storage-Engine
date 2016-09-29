package gerenciador.loger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import gerenciador.arquivos.interfaces.ILog;

public class LogEscrita implements ILog{

	private FileWriter fw;
	
	public LogEscrita(File f) {
		try {
			this.fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void Write(String msg) {
		try {
			fw.write(msg+"\n");
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
