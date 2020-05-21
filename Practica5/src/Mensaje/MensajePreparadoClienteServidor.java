package Mensaje;

public class MensajePreparadoClienteServidor extends Mensaje{
	
	public String _usuarioArchivo;
	public int _port;
	public String _ip;
	
    public MensajePreparadoClienteServidor(String origen, String destino, String usuarioArchivo, String ip, int port)
    {
        super(origen,destino);
        _tipo = 8;
        
        _usuarioArchivo = usuarioArchivo;
        _ip = ip;
        _port = port;
    }

	public String get_usuarioArchivo() {
		return _usuarioArchivo;
	}

	public int get_port() {
		return _port;
	}

	public String get_ip() {
		return _ip;
	}
}