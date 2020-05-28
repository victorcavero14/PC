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
	
	public String toString()
	{
		return "Mensaje lista de usuarios, origen " + _origen + " y destino " + _destino;
	}
}