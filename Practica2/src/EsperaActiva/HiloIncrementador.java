package EsperaActiva;
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
			while(_a.get_dec());
			_a.setIncrementada(true);
			_a.incrementa();
			_a.setIncrementada(false);
			System.out.println("a = " + _a.get_var());
		}
		
	}
}
