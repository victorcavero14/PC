package Servidor;

import Mensaje.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Mensaje.Mensaje;

public class OyenteCliente extends Thread {

	private volatile Servidor _servidor;
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
			
			Boolean cerrado = false;
			
			while(!cerrado)
			{
				Mensaje msj = (Mensaje) objectIn.readObject();

				if(msj instanceof MensajeConexion)
				{
					Usuario user = _servidor.obtenerUsuario(msj.getOrigen());
					
					if(user != null)
					{
						HashMap<ObjectInputStream,ObjectOutputStream> par = new HashMap<ObjectInputStream,ObjectOutputStream>();
						par.put(objectIn, objectOut);

						_servidor.conexionUsuario(user, par);
						objectOut.writeObject(new MensajeConfirmacionConexion(msj.getDestino(), msj.getOrigen()));
					}
					else
					{
						objectOut.writeObject(new MensajeConfirmacionCerrarConexion(msj.getDestino(), msj.getOrigen()));
						cerrado = true;
					}
				}
				else if(msj instanceof MensajeListaUsuarios)
				{
					objectOut.writeObject(new MensajeConfirmacionListaUsuarios(msj.getDestino(), msj.getOrigen(), _servidor.get_usuariosConectados()));
				}
				else if(msj instanceof MensajeCerrarConexion)
				{	
					_servidor.desconexionUsuario( _servidor.obtenerUsuario(msj.getOrigen()));
					objectOut.writeObject(new MensajeConfirmacionCerrarConexion(msj.getDestino(), msj.getOrigen()));
					
					cerrado = true;
				}
				else if(msj instanceof MensajePedirFichero)
				{
					// ESTO QUIZAS HABRIA QUE HACERLO EN EL OYENTECLIENTE 2 
					
					MensajePedirFichero msj_aux = (MensajePedirFichero) msj;
					
					Usuario usuario = _servidor.obtenerUsuario(msj_aux.get_usuarioArchivo());
					HashMap<ObjectInputStream,ObjectOutputStream> map = _servidor.obtenerObjectSocket(usuario);
					
					Map.Entry<ObjectInputStream,ObjectOutputStream> entry = map.entrySet().iterator().next();
					
					entry.getValue().writeObject(new MensajeEmitirFichero(msj.getDestino(), msj_aux.get_usuarioArchivo(), msj.getOrigen(), msj_aux.get_posArchivo()));
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
