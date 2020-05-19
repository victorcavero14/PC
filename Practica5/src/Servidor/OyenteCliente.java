package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import Mensaje.Mensaje;

public class OyenteCliente extends Thread {

	private Servidor _servidor;
	private Socket _socket;

	public OyenteCliente(Socket socket, Servidor servidor) {
		_socket = socket;
		_servidor = servidor;
	}

	public void run() {
		while (true) {
			InputStream inputC;
			try {
				inputC = _socket.getInputStream();
				OutputStream outputC = _socket.getOutputStream();

				InputStreamReader sp = new InputStreamReader(inputC);
			
				System.out.println(new BufferedReader(sp).readLine());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
