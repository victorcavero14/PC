package Mensaje;

public class MensajePedirFichero extends Mensaje {
	
	private String _usuarioArchivo;
	private int _posArchivo;
	
    public MensajePedirFichero(String origen, String destino, String usuarioArchivo, int posArchivo)
    {
        super(origen,destino);
        _tipo = 6;
        _usuarioArchivo = usuarioArchivo;
        _posArchivo = posArchivo;
    }

	public String get_usuarioArchivo() {
		return _usuarioArchivo;
	}

	public int get_posArchivo() {
		return _posArchivo;
	}
}