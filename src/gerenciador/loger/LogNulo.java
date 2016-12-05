package gerenciador.loger;

import gerenciador.arquivos.interfaces.ILog;

public class LogNulo implements ILog {

	@Override
	public void Write(String msg) {
//		throw new RuntimeException("Não implementado");

	}

	@Override
	public void Erro(String msg) {
//		throw new RuntimeException("Não implementado");

	}

}
