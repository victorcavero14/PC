package Cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente {
	
	public String _nombre;
	public String _ip; // InetsocketAddress ??
	public Socket _serverSocket;
	
	// FLUJO:
	public BufferedReader _serverBR;
	public BufferedWriter _serverBW;	
	
	
	public Cliente(String nombre, String ip, String host_ip, int port)
	{
		this._nombre = nombre;
		this._ip = ip;
		
		try  
		{
			_serverSocket = new Socket(host_ip, port);
			
			InputStream inputC = _serverSocket.getInputStream();
			_serverBR = new BufferedReader(new InputStreamReader(inputC));
		
			OutputStream outputC = _serverSocket.getOutputStream();
			_serverBW = new BufferedWriter(new OutputStreamWriter(_serverSocket.getOutputStream()));
						
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void comunicacion()
	{
		try {
			
			String recibido = _serverBR.readLine();
			System.out.println("Mensaje recibido: " + recibido);
			_serverBW.write("He recibido tu mensaje!!!!");
			_serverBW.newLine();
			_serverBW.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		try {
			_serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public static void main(String[] args) {
		// ejemplo socket pidiendo el tiempo a server
		
		if(args.length < 2) return;
		
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		
		Cliente cliente = new Cliente("cliente1", "192.168.0.15", hostname, port);	
	}
	*/
}
