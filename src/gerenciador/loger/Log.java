package gerenciador.loger;

import java.io.File;

import gerenciador.arquivos.interfaces.ILog;

public class Log implements ILog {
	public static final File LOG_PATH =
			new File("C:\\Users\\danda_000\\git\\Storage-Engine\\res\\Log");
	
	public void Write(String msg){
		System.out.println(msg);
	}
	
	public void Erro(String msg){
		System.out.println("__" + msg + "__");
	}
}
