package Parte1;

import java.util.concurrent.Semaphore;

public class HiloDecrementador extends Thread{
	
	private int _n; // numero de operaciones
	
	private volatile A _a; // variable compartida
	private volatile Semaphore _sem;
	
	public HiloDecrementador(String string, A a, int N, Semaphore sem) {
		
		super(string);
		_a = a;
		_n = N;
		_sem = sem;
	}

	public void run()
	{		
		for(int i = 0; i < _n; i++)
		{
			try {
				_sem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			_a.decrementa();
			System.out.println("a = " + _a.get_var());
			_sem.release();
		}
	}
}
