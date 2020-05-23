package Servidor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Servidor {

	private String _ip;
	private int _port;
	private ServerSocket _serverSocket;
	
	// LISTA DE USUARIOS GLOBAL
	private volatile List<Usuario> _usuarios;
	
	// LISTA DE USUARIOS CONECTADOS AL SERVIDOR ACTUALMENTE
	private volatile List<Usuario> _usuariosConectados;
	
	// LISTA ID USUARIO CON PAR Fin y Fout
	private volatile HashMap<String, ObjectInputStream> _conexionesInputUsuarios;
	private volatile HashMap<String, ObjectOutputStream> _conexionesOutputUsuarios;

	public Servidor(int port)
	{
		try{
			
			_usuarios = new ArrayList<Usuario>();
			cargarUsuariosArchivo();
			_ip = InetAddress.getLocalHost().getHostAddress();
			_port = port;
			_serverSocket = new ServerSocket(_port);
			_usuariosConectados = new ArrayList<Usuario>();
			_conexionesOutputUsuarios = new HashMap<String,ObjectOutputStream>();
			_conexionesInputUsuarios = new HashMap<String, ObjectInputStream>();

			while(true)
			{	
				
				(new OyenteCliente(_serverSocket.accept(), this)).start();
			}

		}
		catch(UnknownHostException e)
		{
			System.out.println("ERROR NO SE HA PODIDO OBTENER LA IP LOCAL");
		}
		catch(IOException e)
		{
			throw new RuntimeException("No se ha podido crear el servidor", e);
		}
	}
	
	public void cargarUsuariosArchivo()
	{
		// LEEMOS DEL FICHERO USERS Y LO CARGAMOS en _USUARIOS

		try {
			FileInputStream fis = new FileInputStream("users.txt");      
			Scanner sc=new Scanner(fis);    
			
			while(sc.hasNextLine())  
			{  
				String[] usuario = sc.nextLine().split(" ");
				List<String> ficheros = new ArrayList<String>();
				for(int i = 2; i < usuario.length; i++)
				{
					ficheros.add(usuario[i]);
				}
				
				_usuarios.add(new Usuario(usuario[0], usuario[1],ficheros));
			}  
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Fichero users.txt no ha sido encontrado");
		} 
	}

	public Usuario obtenerUsuario(String nombreCliente)
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
	
	public boolean usuarioConectado(String usuario) 
	{
		boolean conectado = false;
		
		for(int i = 0; i < _usuariosConectados.size() && !conectado; i++)
		{
			if(usuario.equals(_usuariosConectados.get(i).get_id()))
			{
				conectado = true;
			}
		}
		return conectado;
	}

	public String obtenerNombreFichero(int posArchivo, String usuarioArchivo) {
		// TODO Auto-generated method stub
		return obtenerUsuario(usuarioArchivo).get_ficheros().get(posArchivo);
	}
	
	public ObjectInputStream obtenerObjectInputSocket(String usuario)
	{
		return _conexionesInputUsuarios.get(usuario);
	}
	
	public ObjectOutputStream obtenerObjectOutputSocket(String usuario)
	{
		return _conexionesOutputUsuarios.get(usuario);
	}
	
	public synchronized void ficheroDescargadoPorUsuario(String nombreCliente, String nombreFichero) {
		
		// No actualizo la base de datos (solo los usuarios conectados) - Habria que modificar el archivo tambien para que sea completo
		
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

	// GETTERS AND SETTERS
	
	public List<Usuario> get_usuariosConectados() {
		return _usuariosConectados;
	}

	public static void main(String[] args) {
		if(args.length < 1) return;
		
		int port = Integer.parseInt(args[0]);

		new Servidor(port);
	}
}
