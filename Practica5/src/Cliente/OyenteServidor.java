package Cliente;

import java.net.Socket;

public class OyenteServidor extends Thread 
{
	// Hilo diferente que establece de manera continua la comunicaci�n con el servidor

	private Socket _socket;

	public OyenteServidor(Socket socket)
	{
		_socket = socket;
	}

	public void run()
	{
		while(true)
		{
			
		}
	}
}
