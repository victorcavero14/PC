package Servidor;

import Mensaje.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

import Mensaje.Mensaje;

public class OyenteCliente extends Thread {

	private Servidor _servidor;
	private Socket _socket;

	public OyenteCliente(Socket socket, Servidor servidor) {
		_socket = socket;
		_servidor = servidor;
	}

	public void run() {
		try {
			InputStream inputC = _socket.getInputStream();
			OutputStream outputC = _socket.getOutputStream();
			
			ObjectOutputStream objectOut = new ObjectOutputStream(outputC);
			ObjectInputStream objectIn = new ObjectInputStream(inputC);
			
			while(true)
			{
				Mensaje msj = (Mensaje) objectIn.readObject();

				if(msj instanceof MensajeConexion)
				{
					MensajeConexion msj_aux = (MensajeConexion) msj;
					Usuario user = _servidor.obtenerUsuario(msj_aux.get_nombreCliente());
					
					if(user != null)
					{
						HashMap<ObjectInputStream,ObjectOutputStream> par = new HashMap<ObjectInputStream,ObjectOutputStream>();
						par.put(objectIn, objectOut);

						_servidor.add_cliente(user, par);
						objectOut.writeObject(new MensajeConfirmacionConexion(msj_aux.getDestino(), msj_aux.getOrigen()));
					}
					else
					{
						objectOut.writeObject(new MensajeConfirmacionCerrarConexion(msj_aux.getDestino(), msj_aux.getOrigen()));
					}
				}
				else if(msj instanceof MensajeListaUsuarios)
				{

				}
				else if(msj instanceof MensajeCerrarConexion)
				{

				}
				else if(msj instanceof MensajePedirFichero)
				{

				}
				else if(msj instanceof MensajeEmitirFichero)
				{

				}
				else if(msj instanceof MensajePreparadoClienteServidor)
				{

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
