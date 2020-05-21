package Mensaje;

public class MensajeListaUsuarios extends Mensaje{
    public MensajeListaUsuarios(String origen, String destino)
    {
        super(origen,destino);
        _tipo = 4;
    }
}