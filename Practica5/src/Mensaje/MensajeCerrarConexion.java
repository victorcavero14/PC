package Mensaje;

public class MensajeCerrarConexion extends Mensaje{
    public MensajeCerrarConexion(String origen, String destino)
    {
        super(origen,destino);
        this._tipo = 1;
    }
    
}