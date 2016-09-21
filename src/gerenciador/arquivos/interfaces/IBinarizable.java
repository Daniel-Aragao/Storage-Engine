package gerenciador.arquivos.interfaces;

public interface IBinarizable <T>{
	public byte[] getByteArray();
	public void fromByteArray(byte[] dados);
}
