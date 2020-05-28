package Servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Mensaje.Mensaje;
import Mensaje.MensajeCerrarConexion;
import Mensaje.MensajeConexion;
import Mensaje.MensajeListaUsuarios;
import Mensaje.MensajePedirFichero;
import Mensaje.MensajePreparadoClienteServidor;

public class MonitorServidor {
	
	// LISTA DE USUARIOS GLOBAL
	private volatile List<Usuario> _usuarios;
		
	// LISTA DE USUARIOS CONECTADOS AL SERVIDOR ACTUALMENTE
	private volatile List<Usuario> _usuariosConectados;
		
	// MAPAS ID USUARIO CON Fin y Fout
	private volatile HashMap<String, ObjectInputStream> _conexionesInputUsuarios;
	private volatile HashMap<String, ObjectOutputStream> _conexionesOutputUsuarios;
	
	public MonitorServidor(List<Usuario> _usuarios, List<Usuario> _usuariosConectados,
			HashMap<String, ObjectInputStream> _conexionesInputUsuarios,
			HashMap<String, ObjectOutputStream> _conexionesOutputUsuarios) {
		this._usuarios = _usuarios;
		this._usuariosConectados = _usuariosConectados;
		this._conexionesInputUsuarios = _conexionesInputUsuarios;
		this._conexionesOutputUsuarios = _conexionesOutputUsuarios;
	}

	public synchronized Usuario obtenerUsuario(String nombreCliente)
	{
		Usuario user = null;
		Boolean encontrado = false;
		
		for(int i = 0; i < _usuarios.size() && !encontrado; i++)
		{
			if(nombreCliente.equals(_usuarios.get(i).get_id()))
			{
				user = _usuarios.get(i);
				encontrado = true;
			}
		}
		
		return user;
	}
	
	public synchronized Usuario obtenerUsuarioConectado(String nombreCliente)
	{
		Usuario user = null;
		boolean conectado = false;
		
		for(int i = 0; i < _usuariosConectados.size() && !conectado; i++)
		{
			if(nombreCliente.equals(_usuariosConectados.get(i).get_id()))
			{
				user = _usuariosConectados.get(i);
				conectado = true;
			}
		}
		return user;
	}
	
	public synchronized void ficheroDescargadoPorUsuario(String nombreCliente, String nombreFichero) {
		
		// Metodo para notificar que un fichero ha sido descargado por cierto usuario
		// No actualizo la base de datos estatica (solo los usuarios conectados)
		
		boolean encontrado = false;
		Usuario user = null;
		int i = 0;
		while(!encontrado && i < _usuariosConectados.size())
		{
			user = _usuariosConectados.get(i);
			if(nombreCliente.equals(user.get_id()))
			{
				List<String> ficheros = user.get_ficheros();
				ficheros.add(nombreFichero);
				List<String> nuevosFich = new ArrayList<String>(ficheros);
				
				_usuariosConectados.add(new Usuario(user.get_id(),user.get_ip(), nuevosFich));
				_usuariosConectados.remove(i);
				encontrado = true;
			}
			i++;
		}
	}
	
	public synchronized void conexionUsuario(Usuario usuario, ObjectInputStream input, ObjectOutputStream output)
	{
		_usuariosConectados.add(usuario);
		_conexionesInputUsuarios.put(usuario.get_id(), input);
		_conexionesOutputUsuarios.put(usuario.get_id(), output);
	}
	
	public synchronized void desconexionUsuario(Usuario usuario)
	{
		_usuariosConectados.remove(usuario);
		_conexionesInputUsuarios.remove(usuario.get_id());
		_conexionesOutputUsuarios.remove(usuario.get_id());
	}
	
	public synchronized boolean usuarioConectado(String usuario) 
	{
		return (obtenerUsuarioConectado(usuario) != null);
	}
	
	public synchronized void imprimeMensaje(Mensaje msj) {
		
		Mensaje msj_aux = msj;
		
		if(msj instanceof MensajeConexion) msj_aux = (MensajeConexion) msj;
		else if(msj instanceof MensajeListaUsuarios) msj_aux = (MensajeListaUsuarios) msj;
		else if(msj instanceof MensajeCerrarConexion) msj_aux = (MensajeCerrarConexion) msj;
		else if(msj instanceof MensajePedirFichero) msj_aux = (MensajePedirFichero) msj;
		else if(msj instanceof MensajePreparadoClienteServidor) msj_aux = (MensajePreparadoClienteServidor) msj;
		
		System.out.println(msj_aux.toString());
	}

	// GETTERS AND SETTERS
	
	public synchronized ObjectInputStream obtenerObjectInputSocket(String usuario)
	{
		return _conexionesInputUsuarios.get(usuario);
	}
	
	public synchronized ObjectOutputStream obtenerObjectOutputSocket(String usuario)
	{
		return _conexionesOutputUsuarios.get(usuario);
	}

	public synchronized String obtenerNombreFichero(int posArchivo, String usuarioArchivo) {
		return obtenerUsuarioConectado(usuarioArchivo).get_ficheros().get(posArchivo);
	}
	
	public synchronized List<Usuario> get_usuariosConectados() {
		return _usuariosConectados;
	}
}
