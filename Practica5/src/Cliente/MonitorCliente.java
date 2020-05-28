package Cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Mensaje.Mensaje;

public class MonitorCliente {
	
	private ObjectOutputStream _serverOOS;
	
	private final Lock _lock;
	private final Condition _noRespuesta;
		
	public MonitorCliente(ObjectOutputStream serverOOS) {
		_lock = new ReentrantLock();
		_noRespuesta = _lock.newCondition();
		_serverOOS = serverOOS;
	}

	public void enviarMensajeEsperarRespuesta(Mensaje msj)
	{
		_lock.lock();
		
		try {
			_serverOOS.writeObject(msj);
			_noRespuesta.await();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No se ha podido enviar el mensaje: ");
			System.out.println(msj.toString());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		_lock.unlock();
	}
	
	public void enviarMensajeNoEsperarRespuesta(Mensaje msj)
	{
		_lock.lock();
		
		try {
			_serverOOS.writeObject(msj);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No se ha podido enviar el mensaje: ");
			System.out.println(msj.toString());
		}
		
		_lock.unlock();
	}

		
	public void mensajeRecibido()
	{
		_lock.lock();
		_noRespuesta.signal();					
		_lock.unlock();
	}
}
