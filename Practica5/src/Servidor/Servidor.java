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
	
	// MAPAS ID USUARIO CON Fin y Fout
	private volatile HashMap<String, ObjectInputStream> _conexionesInputUsuarios;
	private volatile HashMap<String, ObjectOutputStream> _conexionesOutputUsuarios;
	
	// SINCRONIZACION
	private MonitorServidor _monitor;

	public Servidor(int port)
	{
		try{
			_port = port;
			_usuarios = new ArrayList<Usuario>();
			_usuariosConectados = new ArrayList<Usuario>();
			_conexionesOutputUsuarios = new HashMap<String,ObjectOutputStream>();
			_conexionesInputUsuarios = new HashMap<String, ObjectInputStream>();
			
			cargarUsuariosArchivo();
			_monitor = new MonitorServidor(_usuarios, _usuariosConectados, _conexionesInputUsuarios, _conexionesOutputUsuarios);
			
			 _ip = InetAddress.getLocalHost().getHostAddress();
			_serverSocket = new ServerSocket(_port);
			System.out.println("----- Servidor " + _ip + " creado en el puerto " + _port);

			while(true)
			{	
				(new OyenteCliente(_serverSocket.accept(), _monitor)).start();
			}
		}
		catch(UnknownHostException e)
		{
			System.out.println("No se ha podido obtener la ip local.");
			e.printStackTrace();
		}
		catch(IOException e)
		{
			System.out.println("No se ha podido crear el servidor.");
			e.printStackTrace();
		}
	}
	
	public void cargarUsuariosArchivo()
	{
		// LEEMOS DEL FICHERO USERS.TXT Y LO CARGAMOS en _USUARIOS

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
			System.out.println("Fichero users.txt no ha sido encontrado.");
			e.printStackTrace();
		} 
	}

	public static void main(String[] args) {
		if(args.length < 1) return;
		
		int port = Integer.parseInt(args[0]);

		new Servidor(port);
	}
}
