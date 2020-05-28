package Servidor;

import Mensaje.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class OyenteCliente extends Thread {

	private MonitorServidor _monitor;
	private Socket _socket;
	private ObjectOutputStream objectOut;	
	private ObjectInputStream objectIn;


	public OyenteCliente(Socket socket, MonitorServidor monitor) {
		_socket = socket;
		_monitor = monitor;
		
		try {
			objectOut = new ObjectOutputStream(_socket.getOutputStream());
			objectIn = new ObjectInputStream(_socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			Boolean cerrado = false;
			
			while(!cerrado)
			{
				Mensaje msj = (Mensaje) objectIn.readObject();

				if(msj instanceof MensajeConexion)
				{
					Usuario user = _monitor.obtenerUsuario(msj.getOrigen());
					
					if(user != null && !_monitor.usuarioConectado(msj.getOrigen()))
					{
						_monitor.conexionUsuario(user,objectIn, objectOut);
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
					objectOut.writeObject(new MensajeConfirmacionListaUsuarios(msj.getDestino(), msj.getOrigen(), _monitor.get_usuariosConectados()));
				}
				else if(msj instanceof MensajeCerrarConexion)
				{	
					_monitor.desconexionUsuario( _monitor.obtenerUsuarioConectado(msj.getOrigen()));
					objectOut.writeObject(new MensajeConfirmacionCerrarConexion(msj.getDestino(), msj.getOrigen()));
					
					cerrado = true;
				}
				else if(msj instanceof MensajePedirFichero)
				{
					// Insertamos el nuevo fichero del cliente en la base datos (Realmente no lo tiene descargado aun)
					
					MensajePedirFichero msj_aux = (MensajePedirFichero) msj;
					ObjectOutputStream output = _monitor.obtenerObjectOutputSocket(msj_aux.getDestino());
					
					String nombreFichero = _monitor.obtenerNombreFichero(msj_aux.get_posArchivo(), msj_aux.getDestino());
					
					_monitor.ficheroDescargadoPorUsuario(msj.getOrigen(), nombreFichero);
					
					output.writeObject(new MensajeEmitirFichero(msj.getOrigen(), msj.getDestino(), nombreFichero));
				}
				else if(msj instanceof MensajePreparadoClienteServidor)
				{		
					MensajePreparadoClienteServidor msj_aux = (MensajePreparadoClienteServidor) msj;
					ObjectOutputStream output = _monitor.obtenerObjectOutputSocket(msj_aux.getDestino());
				
					output.writeObject(new MensajePreparadoServidorCliente(msj.getOrigen(), msj_aux.getDestino(), msj_aux.get_ip(), msj_aux.get_port()));
				}
				
				_monitor.imprimeMensaje(msj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
