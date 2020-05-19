package Mensaje;

public class MensajeListaUsuarios extends Mensaje{
    public MensajeListaUsuarios(String origen, String destino)
    {
        super(origen,destino);
        this._tipo = 1;
    }
}