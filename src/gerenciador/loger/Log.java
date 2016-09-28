package gerenciador.loger;

import java.io.File;

public class Log {
	public static final File LOG_PATH =
			new File("C:\\Users\\danda_000\\git\\Storage-Engine\\res\\Log");
	
	public static void Write(String msg){
		System.out.println(msg);
	}
	
	public static void Erro(String msg){
		System.out.println("__" + msg + "__");
	}
}
