package Cliente;

import Mensaje.*;
import Servidor.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class OyenteServidor extends Thread 
{
	private Socket _socket;
	private Cliente _cliente;
	private ObjectInputStream _serverOIS;
	private MonitorCliente _monitor;

	public OyenteServidor(Socket socket, Cliente cliente)
	{
		_socket = socket;
		_cliente = cliente;
		
		try {
			_serverOIS = new ObjectInputStream(_socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
					MensajeEmitirFichero msj_aux = (MensajeEmitirFichero) msj;
					Emisor emisor = new Emisor(msj_aux.get_nombreArchivo(), _cliente.get_nombre());
					emisor.start();
					
					_cliente.preparadoClienteServidor(msj_aux.getOrigen(), emisor.get_port());
				}
				else if(msj instanceof MensajePreparadoServidorCliente)
				{
					MensajePreparadoServidorCliente msj_aux = (MensajePreparadoServidorCliente) msj;
					
					new Receptor(msj_aux.get_ip(),msj_aux.get_port(), _cliente.get_nombre()).start();
				}
				
				_monitor.mensajeRecibido();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
