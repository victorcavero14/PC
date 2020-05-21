package Cliente;

import Mensaje.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cliente {

	private String _nombre;
	private String _ip;
	private Socket _socket;
	private Scanner _sc;

	// FLUJO:
	private ObjectOutputStream _serverOOS;
	private ObjectInputStream _serverOIS;
	
	// SINCRONIZACION: 
	
	private boolean _conexionConfirmada;
	private MonitorCliente _monitor;

	
	public Cliente(String ip, String host_ip, int port) {
		
		_conexionConfirmada = false;
		_sc = new Scanner(System.in);
		
		System.out.print("Introduce tu nombre como cliente: ");
		_nombre = _sc.next();
		
		_ip = ip;
		
		_monitor = new MonitorCliente();
		
		
		try {
			_socket = new Socket(host_ip, port);

			InputStream inputC = _socket.getInputStream();
			OutputStream outputC = _socket.getOutputStream();
			
			_serverOIS = new ObjectInputStream(inputC);
			_serverOOS = new ObjectOutputStream(outputC);
			
			Thread th = (new OyenteServidor(_socket, this)); 
			th.start();
			
			protocoloDeInicio();
			
			_sc.close();
			th.join();
			_socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void protocoloDeInicio()
	{
		conexion();
		
		if(_conexionConfirmada)
		{
			menu();
			cerrarConexion();
		}
	}
	
	public void menu()
	{
		StringBuilder sb = new StringBuilder();
		String opcion = "";
		
		sb.append(System.lineSeparator());
		sb.append("CLIENTE " + _nombre + " " + _ip);
		sb.append(System.lineSeparator());
		sb.append("OPCIONES DISPONILBLES: ");
		sb.append(System.lineSeparator());
		sb.append("0 --- SALIR");
		sb.append(System.lineSeparator());
		sb.append("1 --- Lista de usuarios conectados al servidor");
		sb.append(System.lineSeparator());
		sb.append("2 --- Pedir fichero");
		sb.append(System.lineSeparator());
		sb.append("OPCION: ");
		
		while(!opcion.equals("0"))
		{
			System.out.print(sb.toString());
			opcion = _sc.next();
			
			if(opcion.equals("1"))
			{
				listaUsuarios();
			}
			else if(opcion.equals("2"))
			{
				System.out.println();
				System.out.print("Introduce el nombre y posicion del archivo (Separados por un espacio): ");
				String datos = _sc.next();
				String[] arr = datos.split(" ");
				
				pedirFichero(arr[0], Integer.parseInt(arr[1]));
			}
			
			// MENSAJE PREPARADO CLIENTE SERVIDOR
			// AÑADIR MODIFICACION DE TABLAS!! CLIENTE TIENE NUIEVOS FICHEROS... (OPCIONAL...)
		}
	}

	public synchronized void enviarMensaje(Mensaje msj)
	{
		try {
			_serverOOS.writeObject(msj);
			_monitor.mensajeEnviado();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No se ha podido enviar el mensaje: ");
			System.out.println(msj.toString());
		}
	}

	public void conexion() {
		MensajeConexion msj = new MensajeConexion(_nombre, _socket.getInetAddress().getHostName());
		enviarMensaje(msj);
	}

	public void cerrarConexion() {
		MensajeCerrarConexion msj = new MensajeCerrarConexion(_nombre, _socket.getInetAddress().getHostName());
		enviarMensaje(msj);	
	}
	
	public void listaUsuarios() {
		MensajeListaUsuarios msj = new MensajeListaUsuarios(_nombre, _socket.getInetAddress().getHostName());
		enviarMensaje(msj);
	}
	
	public void pedirFichero(String nombreUsuario, int pos)
	{
		MensajePedirFichero msj = new MensajePedirFichero(_nombre, _socket.getInetAddress().getHostName(), nombreUsuario, pos);
		enviarMensaje(msj);
	}
	
	public void preparadoClienteServidor()
	{
		MensajePreparadoClienteServidor msj = new MensajePreparadoClienteServidor(_nombre, _socket.getInetAddress().getHostName());
		enviarMensaje(msj);
	}
	
	// GETTERS AND SETTERS
	
	public ObjectOutputStream get_serverOOS() {
		return _serverOOS;
	}

	public ObjectInputStream get_serverOIS() {
		return _serverOIS;
	}

	public MonitorCliente get_monitor() {
		return _monitor;
	}
	
	public void set_conexionConfirmada(boolean _conexionConfirmada) {
		this._conexionConfirmada = _conexionConfirmada;
	}

	public static void main(String[] args) {
		if (args.length < 2)
			return; 

		try {
			String hostname = args[0];
			int port = Integer.parseInt(args[1]);
			String ip = InetAddress.getLocalHost().getHostAddress();

			new Cliente(ip, hostname, port);	

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
