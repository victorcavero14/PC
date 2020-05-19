package Mensaje;

public class MensajePedirFichero extends Mensaje {
    public MensajePedirFichero(String origen, String destino)
    {
        super(origen,destino);
        this._tipo = 1;
    }
}