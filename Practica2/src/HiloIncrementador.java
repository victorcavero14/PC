public class HiloIncrementador extends Thread {
	
	public A _a;
	public int _n;
	
	public HiloIncrementador(String string, A a, int N) {
		
		super(string);
		_a = a;
		_n = N;
	}

	public void run()
	{
		for(int i = 0; i < _n; i++) 
		{
			_a.incrementa();
			System.out.println("a = " + _a.get_var());
		}
		
	}
}
