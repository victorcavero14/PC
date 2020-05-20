package Cliente;

import Mensaje.*;

import java.io.IOException;
import java.net.Socket;

public class OyenteServidor extends Thread 
{
	// Hilo diferente que establece de manera continua la comunicaci�n con el servidor

	private Socket _socket;
	private Cliente _cliente;


	public OyenteServidor(Socket socket, Cliente cliente)
	{
		_socket = socket;
		_cliente = cliente;
	}

	public void run()
	{
		try {
			while(true)
			{
				Mensaje msj = (Mensaje) _cliente.get_serverOIS().readObject();
				
				if(msj instanceof MensajeConfirmacionConexion)
				{
					System.out.println("Conexion establecida con el servidor");
					_cliente.conexionConfirmada();
				}
				else if(msj instanceof MensajeConfirmacionCerrarConexion)
				{
					System.out.println("Sesion terminada. Adios.");
				}
				else if(msj instanceof MensajeConfirmacionListaUsuarios)
				{
					
				}
				else if(msj instanceof MensajeEmitirFichero)
				{
					
				}
				else if(msj instanceof MensajePreparadoServidorCliente)
				{
					
				}
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
