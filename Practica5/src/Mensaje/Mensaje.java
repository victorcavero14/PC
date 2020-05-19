package Mensaje;

public abstract class Mensaje {
	
	/*
	Tipos de mensajes
		0 - Conexión
		1 - Confirmacion conexión
		2 - Cerrar conexión
		3 - Confirmacion cerrar conexión
		4 - Lista Usuarios
		5 - Confirmación lista de usuarios
		6 - Pedir Fichero
		7 - Emitir Fichero
		8 - Preparado Cliente Servidor
		9 - Preparado Servidor Cliente
	*/

	protected int _tipo;
	protected String _origen;
	protected String _destino;

	public Mensaje(String origen, String destino)
	{
		_origen = origen;
		_destino = destino;
	}

	public int getTipo()
	{
		return _tipo;
	}
	
	public String getOrigen()
	{
		return _origen;
	}
	
	public String getDestino()
	{
		return _destino;
	}

}
