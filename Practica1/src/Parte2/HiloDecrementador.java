package Parte2;

public class HiloDecrementador extends Thread{
	
	public A _a;
	public long _sleeptime;
	
	public HiloDecrementador(String string, long sleep, A a) {
		
		super(string);
		
		_sleeptime = sleep;
		_a = a;
	}

	public void run()
	{
		//System.out.println("Mi id es: " + getId() + " y mi nombre: " + getName());
		
		_a.decrementa();
		System.out.println("a = " + _a.get_var());
		
		try {
			sleep(_sleeptime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Mi id es: " + getId() + " y mi nombre: " + getName());
		
	}
}
