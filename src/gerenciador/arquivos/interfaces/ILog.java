package gerenciador.arquivos.interfaces;

import java.io.File;

public interface ILog {
	public static final File LOG_PATH =
//			new File("C:\\Users\\danda_000\\git\\Storage-Engine\\res\\Log");
			new File("C:\\Users\\pedro\\Documents\\GitHub\\Storage-Engine\\res\\Log");
	public void Write(String msg);
	public void Erro(String msg);
}