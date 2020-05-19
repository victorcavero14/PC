package Cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	private String _nombre;
	private String _ip; // InetsocketAddress ??
	private Socket _socket;

	// FLUJO:
	private BufferedReader _serverBR;
	private PrintWriter _serverPW;

	public Cliente(String nombre, String ip, String host_ip, int port) {
		_nombre = nombre;
		_ip = ip;

		try {
			_socket = new Socket(host_ip, port);

			InputStream inputC = _socket.getInputStream();
			_serverBR = new BufferedReader(new InputStreamReader(inputC));

			OutputStream outputC = _socket.getOutputStream();
			_serverPW = new PrintWriter(outputC, true);

			start();
			//(new OyenteServidor(_socket)).start();

			// OutputStream outputC = _socket.getOutputStream();
			// _serverBW = new BufferedWriter(new
			// OutputStreamWriter(_socket.getOutputStream()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		_serverPW.println("Soy el cliente " + _nombre + " y he establecido conexión.");

		try {
			System.out.println("Respuesta del servidor: " + _serverBR.readLine());
		} catch (IOException e) {
			System.out.println("ERROR: NO HE TENIDO UNA CONEXION CORRECTA CON EL SERVIDOR");
		}
	}

	public void stop() {
		_serverPW.println("Soy el cliente " + _nombre + " y quiero finalizar la conexion");

		try {
			System.out.println("Respuesta del servidor: " + _serverBR.readLine());
			_socket.close();
		} catch (IOException e) {
			System.out.println("ERROR: NO HE PODIDO TERMINAR LA CONEXION");
		}
	}

	public void enviar_mensaje(String mensaje) {
		try {
			_serverPW.println(mensaje);
			System.out.println("Respuesta del servidor: " + _serverBR.readLine());
		} catch (IOException e) {
			System.out.println("ERROR: NO HE PODIDO ENVIAR EL MENSAJE");
		}
	}

	public static void main(String[] args) {
		if (args.length < 2)
			return;

		try {
			String hostname = args[0];
			int port = Integer.parseInt(args[1]);
			String ip = InetAddress.getLocalHost().getHostAddress();

			Scanner sc = new Scanner(System.in);
			System.out.println("Introduce tu nombre como cliente: ");
			String nombre = sc.nextLine();
			sc.close();

			new Cliente(nombre, ip, hostname, port);	

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
}
