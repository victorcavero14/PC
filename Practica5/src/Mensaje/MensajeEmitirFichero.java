package Mensaje;

public class MensajeEmitirFichero extends Mensaje {
	
	private String _usuarioArchivo;
	private int _posArchivo;
	
    public MensajeEmitirFichero (String origen, String destino, String usuarioArchivo, int posArchivo)
    {
        super(origen,destino);
        _tipo = 7;
        
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