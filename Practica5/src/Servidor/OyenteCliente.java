package Servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class OyenteCliente extends Thread {

	private Servidor _servidor;
	private Socket _socket;

	public OyenteCliente(Socket socket, Servidor servidor)
	{
		_socket = socket;
		_servidor = servidor;
	}

	public void run()
	{
		while(true)
		{
			Mensaje m = (Mensaje) fin.readObject();

			if(m == Mensaje.START)
			{
				InputStream inputC = _socket.getInputStream();
				OutputStream outputC = _socket.getOutputStream();

				HashMap<BufferedReader,PrintWriter> _par = new HashMap<BufferedReader,PrintWriter>();
				_par.put(new BufferedReader(new InputStreamReader(inputC)),new PrintWriter(outputC, true));	

				_servidor.add_cliente(Usuario, _par);
			}
			else if(m == Mensaje.LISTA_USUARIOS)
			{

			}
			else if(m == Mensaje.CLOSE)
			{

			}
			else if(m == Mensaje.PEDIR_FICHERO)
			{

			}
			else if(m == Mensaje.EMITIR_FICHERO)
			{

			}
			else if(m == Mensaje.PREPARADO_CLIENTE_SERVIDOR)
			{

			}
		}
	}

}
