package Mensaje;

public class MensajeConfirmacionCerrarConexion extends Mensaje{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MensajeConfirmacionCerrarConexion(String origen, String destino)
    {
        super(origen,destino);
        _tipo = 3;
    }
}