package Mensaje;

public class MensajeConfirmacionConexion extends Mensaje{
    public MensajeConfirmacionConexion(String origen, String destino)
    {
        super(origen,destino);
        this._tipo = 1;
    }
}