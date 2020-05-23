package Servidor;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String _ip;
	private List<String> _ficheros;
	
	public Usuario(String id, String ip, List<String> ficheros)
	{
		_id = id;
		_ip = ip;
		_ficheros = ficheros;
	}

	@Override
	public String toString() {
		return "Usuario [_id=" + _id + ", _ip=" + _ip + ", _ficheros=" + _ficheros + "]";
	}

	public String get_id()
	{
		return _id;
	}

	public String get_ip()
	{
		return _ip;
	}

	public List<String> get_ficheros()
	{
		return _ficheros;
	}

	public void insertarFichero(String nombreFichero) {
		_ficheros.add(nombreFichero);
	}

}
