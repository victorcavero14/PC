package Mensaje;

public class MensajeConfirmacionCerrarConexion extends Mensaje{
    public MensajeConfirmacionCerrarConexion(String origen, String destino)
    {
        super(origen,destino);
        this._tipo = 1;
    }
}