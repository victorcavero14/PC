package Cliente;

import Mensaje.*;
import Servidor.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class OyenteServidor extends Thread 
{
	// Hilo diferente que establece de manera continua la comunicaci�n con el servidor

	private Socket _socket;
	private Cliente _cliente;
	private ObjectOutputStream _serverOOS;
	private ObjectInputStream _serverOIS;
	private MonitorCliente _monitor;


	public OyenteServidor(Socket socket, Cliente cliente)
	{
		_socket = socket;
		_cliente = cliente;
		_serverOOS = cliente.get_serverOOS();
		_serverOIS = cliente.get_serverOIS();
		_monitor = cliente.get_monitor();
	}

	public void run()
	{
		Boolean cerrado = false;
		try {
			while(!cerrado)
			{
				Mensaje msj = (Mensaje) _serverOIS.readObject();
				
				if(msj instanceof MensajeConfirmacionConexion)
				{
					System.out.println("Conexion establecida con el servidor");
					_cliente.set_conexionConfirmada(true);
				}
				else if(msj instanceof MensajeConfirmacionCerrarConexion)
				{
					System.out.println("Sesion terminada. Adios.");
					cerrado = true;
				}
				else if(msj instanceof MensajeConfirmacionListaUsuarios)
				{
					MensajeConfirmacionListaUsuarios msj_aux = (MensajeConfirmacionListaUsuarios) msj;
					List<Usuario> lista = msj_aux.get_listaUsuarios();
					
					System.out.println("LISTA DE USUARIOS OBTENIDA: ");
					for(int i = 0; i < lista.size(); i++) System.out.println(lista.get(i).toString());
				}
				else if(msj instanceof MensajeEmitirFichero)
				{
					
				}
				else if(msj instanceof MensajePreparadoServidorCliente)
				{
					
				}
				
				_monitor.mensajeRecibido();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
