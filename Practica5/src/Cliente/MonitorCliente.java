package Cliente;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorCliente {
	
	private final Lock _lock;
	private final Condition _noRespuesta;
		
	public MonitorCliente() {
		_lock = new ReentrantLock();
		_noRespuesta = _lock.newCondition();
	}
		
	public void mensajeEnviado()
	{
		_lock.lock();
		
		try {
			_noRespuesta.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
