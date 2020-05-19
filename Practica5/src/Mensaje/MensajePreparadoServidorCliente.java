package Mensaje;

public class MensajePreparadoServidorCliente extends Mensaje{
    public MensajePreparadoServidorCliente(String origen, String destino)
    {
        super(origen,destino);
        this._tipo = 9;
    }
}