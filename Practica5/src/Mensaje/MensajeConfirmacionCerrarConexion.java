package Mensaje;

public class MensajeConfirmacionCerrarConexion extends Mensaje{
    public MensajeConfirmacionCerrarConexion(String origen, String destino)
    {
        super(origen,destino);
        _tipo = 3;
    }
}