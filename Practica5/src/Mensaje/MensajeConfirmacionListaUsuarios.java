package Mensaje;

public class MensajeConfirmacionListaUsuarios extends Mensaje{
    public MensajeConfirmacionListaUsuarios (String origen, String destino)
    {
        super(origen,destino);
        this._tipo = 5;
    }
}