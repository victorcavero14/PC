package Mensaje;

public class MensajeConexion extends Mensaje{
	
	private String _nombreCliente;
	
    public MensajeConexion(String origen, String destino, String nombreCliente)
    {
        super(origen,destino);
        this._tipo = 0;
        _nombreCliente = nombreCliente;
    }
    
    public String get_nombreCliente()
    {
    	return _nombreCliente;
    }
}