package Cliente;

import Mensaje.*;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	private String _nombre;
	private String _ip;
	private Socket _socket;
	private Scanner _sc;

	// FLUJO:
	private ObjectOutputStream _serverOOS;
	
	// SINCRONIZACION: 
	private boolean _conexionConfirmada;
	private MonitorCliente _monitor;

	
	public Cliente(String ip, String host_ip, int port) {
		
		_ip = ip;
		_conexionConfirmada = false;
		_sc = new Scanner(System.in);
		
		System.out.print("Introduce tu nombre como cliente: ");
		_nombre = _sc.nextLine();		
		
		try {
			_socket = new Socket(host_ip, port);
			_serverOOS = new ObjectOutputStream(_socket.getOutputStream());
			
			_monitor = new MonitorCliente(_serverOOS);
			
			Thread th = (new OyenteServidor(_socket, this)); 
			th.start();
			
			protocoloDeInicio();
			
			_sc.close();
			th.join();
			_socket.close();
			
		} catch (Exception e) {
			System.out.println("No se ha podido establecer la conexion con el servidor");
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
		String opcion = "-1";
		
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
			opcion = _sc.nextLine();
			
			if(opcion.equals("1"))
			{
				listaUsuarios();
			}
			else if(opcion.equals("2"))
			{
				System.out.print("Introduce el nombre del usuario: ");
				String usuario = _sc.nextLine();
				System.out.print("Posicion del archivo (0,1,2...) : ");
				String pos = _sc.nextLine();	
				
				pedirFichero(usuario, Integer.parseInt(pos));
			}
		}
	}

	public void conexion() {
		MensajeConexion msj = new MensajeConexion(_nombre, _socket.getInetAddress().getHostName());
		_monitor.enviarMensajeEsperarRespuesta(msj);
	}

	public void cerrarConexion() {
		MensajeCerrarConexion msj = new MensajeCerrarConexion(_nombre, _socket.getInetAddress().getHostName());
		_monitor.enviarMensajeEsperarRespuesta(msj);	
	}
	
	public void listaUsuarios() {
		MensajeListaUsuarios msj = new MensajeListaUsuarios(_nombre, _socket.getInetAddress().getHostName());
		_monitor.enviarMensajeEsperarRespuesta(msj);
	}
	
	public void pedirFichero(String destino, int pos)
	{
		MensajePedirFichero msj = new MensajePedirFichero(_nombre, destino, pos);
		_monitor.enviarMensajeEsperarRespuesta(msj);
	}
	
	public void preparadoClienteServidor(String destino, int port)
	{
		MensajePreparadoClienteServidor msj = new MensajePreparadoClienteServidor(_nombre, destino, _ip, port);
		_monitor.enviarMensajeNoEsperarRespuesta(msj);
	}
	
	// GETTERS AND SETTERS
	
	public MonitorCliente get_monitor() {
		return _monitor;
	}
	
	public String get_nombre()
	{
		return _nombre;
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
