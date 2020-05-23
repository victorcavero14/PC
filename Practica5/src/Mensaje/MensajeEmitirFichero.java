package Mensaje;

public class MensajeEmitirFichero extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _nombreArchivo;
	
    public MensajeEmitirFichero (String origen, String destino, String nombreArchivo)
    {
        super(origen,destino);
        _tipo = 7;
        
        _nombreArchivo = nombreArchivo;
    }

	public String get_nombreArchivo() {
		return _nombreArchivo;
	}
}