package Mensaje;

public class MensajeEmitirFichero extends Mensaje {
    public MensajeEmitirFichero (String origen, String destino)
    {
        super(origen,destino);
        this._tipo = 1;
    }
}