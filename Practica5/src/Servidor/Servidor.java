package Servidor;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Servidor {

	private String _ip;
	private int _port;
	private ServerSocket _serverSocket;
	// LISTA DE USUARIOS GLOBAL
	private List<Usuario> _listaUsuarios;
	// LISTA ID USUARIO CON PAR Fin y Fout
	private HashMap<String, HashMap<BufferedReader, PrintWriter>> _conexionesUsuarios; 

	public Servidor(int port)
	{
		try{
			_ip = InetAddress.getLocalHost().getHostAddress();
			_port = port;
			ServerSocket serversocket = new ServerSocket(_port);
			_serverSocket = serversocket;
			_listaUsuarios = new ArrayList<Usuario>();
			_conexionesUsuarios = new HashMap<String, HashMap<BufferedReader,PrintWriter>>();

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

	public void add_cliente(Usuario usuario, HashMap<BufferedReader,PrintWriter> par)
	{
		_listaUsuarios.add(usuario);
		_conexionesUsuarios.put(usuario.get_id(), par);
	}

	// GETTERS AND SETTERS

	public List<Usuario> get_listaUsuarios()
	{
		return _listaUsuarios;
	}

	public HashMap<String, HashMap<BufferedReader, PrintWriter>> get_conexionesUsuarios()
	{
		return _conexionesUsuarios;
	}

	public static void main(String[] args) {
		if(args.length < 1) return;
		
		int port = Integer.parseInt(args[0]);

		new Servidor(port);
	}
}
