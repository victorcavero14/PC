package Servidor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Servidor {

	private String _ip;
	private int _port;
	private ServerSocket _serverSocket;
	
	private List<Usuario> _usuarios;
	// LISTA DE USUARIOS GLOBAL
	private List<Usuario> _usuariosConectados;
	// LISTA ID USUARIO CON PAR Fin y Fout
	private HashMap<String, HashMap<ObjectInputStream, ObjectOutputStream>> _conexionesUsuarios; 

	public Servidor(int port)
	{
		try{
			
			_usuarios = new ArrayList<Usuario>();
			carga_usuarios();
			_ip = InetAddress.getLocalHost().getHostAddress();
			_port = port;
			_serverSocket = new ServerSocket(_port);
			_usuariosConectados = new ArrayList<Usuario>();
			_conexionesUsuarios = new HashMap<String, HashMap<ObjectInputStream,ObjectOutputStream>>();

			while(true)
			{
				Socket clientesocket = _serverSocket.accept();
				(new OyenteCliente(clientesocket, this)).start();
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
	
	public void carga_usuarios()
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
	
	public void add_cliente(Usuario usuario, HashMap<ObjectInputStream,ObjectOutputStream> par)
	{
		_usuariosConectados.add(usuario);
		_conexionesUsuarios.put(usuario.get_id(), par);
	}

	// GETTERS AND SETTERS

	

	public static void main(String[] args) {
		if(args.length < 1) return;
		
		int port = Integer.parseInt(args[0]);

		new Servidor(port);
	}
}
