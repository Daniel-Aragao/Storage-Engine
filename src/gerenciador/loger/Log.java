package gerenciador.loger;

public class Log {

	public static void Write(String msg){
		System.out.println(msg);
	}
	
	public static void Erro(String msg){
		System.err.println(msg);
	}
}
