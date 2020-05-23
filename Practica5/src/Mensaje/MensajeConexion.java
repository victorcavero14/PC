package Mensaje;

public class MensajeConexion extends Mensaje{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MensajeConexion(String origen, String destino)
    {
        super(origen,destino);
        _tipo = 0;
    }
}