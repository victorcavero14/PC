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

public class Cliente {

	private String _nombre;
	private String _ip; // InetsocketAddress ??
	private Socket _socket;
	private Scanner _sc;

	// FLUJO:
	private ObjectOutputStream _serverOOS;
	private ObjectInputStream _serverOIS;
	
	// SINCRONIZACION: 
	private boolean _conexionConfirmada;

	public Cliente(String ip, String host_ip, int port) {
		
		_conexionConfirmada = false;
		_sc = new Scanner(System.in);
		
		System.out.print("Introduce tu nombre como cliente: ");
		_nombre = _sc.next();
		
		_ip = ip;
		
		try {
			_socket = new Socket(host_ip, port);

			InputStream inputC = _socket.getInputStream();
			OutputStream outputC = _socket.getOutputStream();
			
			_serverOIS = new ObjectInputStream(inputC);
			_serverOOS = new ObjectOutputStream(outputC);
			
			(new OyenteServidor(_socket, this)).start();
			
			conexion();
			while(!_conexionConfirmada); // ESTO QUIZAS SE PUEDA HACER MEJOR CON LOCKS! ?
			menu();
			cerrarConexion();
			_sc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void menu()
	{
		StringBuilder sb = new StringBuilder();
		String opcion = "";
		
		sb.append("MENU DE " + _nombre + ", OPCIONES DISPONILBLES: ");
		sb.append(System.lineSeparator());
		sb.append("0 --- SALIR");
		sb.append(System.lineSeparator());
		sb.append("1 --- Lista de Usuarios");
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
				pedirFichero();
			}
		}
	}
	
	public void enviarMensaje(Mensaje msj)
	{
		try {
			_serverOOS.writeObject(msj);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No se ha podido enviar el mensaje: ");
			System.out.println(msj.toString());
		}	
	}

	public void conexion() {
		MensajeConexion msj = new MensajeConexion(_ip, _socket.getInetAddress().getHostAddress(), _nombre);
		enviarMensaje(msj);
	}

	public void cerrarConexion() {
		MensajeCerrarConexion msj = new MensajeCerrarConexion(_ip, _socket.getInetAddress().getHostAddress());
		enviarMensaje(msj);	
	}
	
	public void listaUsuarios() {
		MensajeListaUsuarios msj = new MensajeListaUsuarios(_ip, _socket.getInetAddress().getHostAddress());
		enviarMensaje(msj);
	}
	
	public void pedirFichero()
	{
		MensajePedirFichero msj = new MensajePedirFichero(_ip, _socket.getInetAddress().getHostAddress());
		enviarMensaje(msj);
	}
	
	public void preparadoClienteServidor()
	{
		MensajePreparadoClienteServidor msj = new MensajePreparadoClienteServidor(_ip, _socket.getInetAddress().getHostAddress());
		enviarMensaje(msj);
	}

	public ObjectInputStream get_serverOIS() {
		return _serverOIS;
	}
	
	public void conexionConfirmada()
	{
		_conexionConfirmada = true;
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
