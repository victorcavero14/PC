package Mensaje;

public class MensajeCerrarConexion extends Mensaje{	
    public MensajeCerrarConexion(String origen, String destino)
    {
        super(origen,destino);
        _tipo = 2;
    }
    
}