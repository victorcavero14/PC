package Funcionality;
import LockImplementations.Lock;

public class HiloIncrementador extends Thread {
	
	public A _a;
	public int _n;
	Lock _lock;
	
	public HiloIncrementador(String string, A a, int N, Lock lock) {
		
		super(string);
		_a = a;
		_n = N;
		_lock = lock;
	}

	public void run()
	{
		for(int i = 0; i < _n; i++) 
		{
			_lock.takeLock();
			_a.incrementa();
			System.out.println("a = " + _a.get_var());
			_lock.releaseLock();
		}
		
	}
}
