package Mensaje;

public class MensajePreparadoServidorCliente extends Mensaje{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int _port;
	public String _ip;
	
    public MensajePreparadoServidorCliente(String origen, String destino, String ip, int port)
    {
        super(origen,destino);
        _tipo = 9;
        
        _port = port;
        _ip = ip;
    }

	public int get_port() {
		return _port;
	}

	public String get_ip() {
		return _ip;
	}
}