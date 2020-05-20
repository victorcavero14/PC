package Mensaje;

public class MensajePreparadoClienteServidor extends Mensaje{
    public MensajePreparadoClienteServidor(String origen, String destino)
    {
        super(origen,destino);
        this._tipo = 8;
    }
}