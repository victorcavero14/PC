package Parte1;

import java.util.concurrent.atomic.AtomicBoolean;

public class HiloIncrementador extends Thread {
	
	private int _n; // numero de operaciones
	
	private volatile A _a; // variable compartida
	private volatile AtomicBoolean _lock; // variable booleana de sincronizacion 
	
	public HiloIncrementador(String string, A a, int N, AtomicBoolean lock) {
		super(string);
		_a = a;
		_n = N;
		_lock = lock;
	}

	public void run()
	{
		for(int i = 0; i < _n; i++) 
		{
			try {
				while(_lock.get()) sleep(100);
				while(_lock.getAndSet(true))
				{
					while(_lock.get()) sleep(100); 
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			_a.incrementa();
			System.out.println("a = " + _a.get_var());
			
			_lock.set(false);
		}
	}
}
