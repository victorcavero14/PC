package Mensaje;

public class MensajePedirFichero extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _posArchivo;
	
    public MensajePedirFichero(String origen, String destino, int posArchivo)
    {
        super(origen,destino);
        _tipo = 6;
        _posArchivo = posArchivo;
    }

	public int get_posArchivo() {
		return _posArchivo;
	}
	
	public String toString()
	{
		return "Solicitud del fichero " + _posArchivo + ", propiedad de " + _destino + " y para " + _origen;
	}
}