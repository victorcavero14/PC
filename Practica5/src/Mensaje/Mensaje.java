package Mensaje;

import java.io.Serializable;

public abstract class Mensaje implements Serializable{
	
	/*
	Tipos de mensajes
		0 - Conexion
		1 - Confirmacion conexion
		2 - Cerrar conexion
		3 - Confirmacion cerrar conexion
		4 - Lista Usuarios
		5 - Confirmacion lista de usuarios
		6 - Pedir Fichero
		7 - Emitir Fichero
		8 - Preparado Cliente Servidor
		9 - Preparado Servidor Cliente
	*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	public String toString()
	{
		return "TIPO: " + _tipo + ", ORIGEN: " + _origen + ", DESTINO: " + _destino;
	}

}
