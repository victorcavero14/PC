public class HiloIncrementador extends Thread {
	
	public A _a;
	public int _n;
	LockRompeEmpate _lock;
	
	public HiloIncrementador(String string, A a, int N, LockRompeEmpate lock) {
		
		super(string);
		_a = a;
		_n = N;
		_lock = lock;
	}

	public void run()
	{
		for(int i = 0; i < _n; i++) 
		{
			_lock.takeLock(Integer.parseInt(this.getName()));
			_a.incrementa();
			System.out.println("a = " + _a.get_var());
			_lock.releaseLock(Integer.parseInt(this.getName()));
		}
		
	}
}
