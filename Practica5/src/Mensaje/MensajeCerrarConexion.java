package Mensaje;

public class MensajeCerrarConexion extends Mensaje{	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MensajeCerrarConexion(String origen, String destino)
    {
        super(origen,destino);
        _tipo = 2;
    }
    
}