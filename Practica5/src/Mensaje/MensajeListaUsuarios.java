package Mensaje;

public class MensajeListaUsuarios extends Mensaje{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MensajeListaUsuarios(String origen, String destino)
    {
        super(origen,destino);
        _tipo = 4;
    }
}