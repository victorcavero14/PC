package Parte1IncrementosYDecrementos;

public class HiloDecrementador extends Thread{
	
	public A _a; // variable compartida
	public int _n; // numero de operaciones
	MonitorHilos _monitor;
	
	public HiloDecrementador(String string, A a, int N, MonitorHilos monitor) {
		
		super(string);
		_a = a;
		_n = N;
		_monitor = monitor;
	}

	public void run()
	{		
		for(int i = 0; i < _n; i++)
		{
			_monitor.decrementaValor(_a);
			
			/*
			_lock.takeLock();
			_a.decrementa();
			System.out.println("a = " + _a.get_var());
			_lock.releaseLock();
			*/
		}
	}
}
