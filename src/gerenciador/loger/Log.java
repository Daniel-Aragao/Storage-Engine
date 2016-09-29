package gerenciador.loger;

import gerenciador.arquivos.interfaces.ILog;

public class Log implements ILog {
	
	
	public void Write(String msg){
		System.out.println(msg);
	}
	
	public void Erro(String msg){
		System.out.println("__" + msg + "__");
	}
}
