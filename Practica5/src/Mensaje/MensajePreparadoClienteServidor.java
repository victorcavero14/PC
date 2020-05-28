package Mensaje;

public class MensajePreparadoClienteServidor extends Mensaje{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int _port;
	public String _ip;
	
    public MensajePreparadoClienteServidor(String origen, String destino, String ip, int port)
    {
        super(origen,destino);
        _tipo = 8;
        
        _ip = ip;
        _port = port;
    }

	public int get_port() {
		return _port;
	}

	public String get_ip() {
		return _ip;
	}
	
	public String toString()
	{
		return "Mensaje preparado cliente servidor, origen " + _origen + " y destino " + _destino;
	}
}