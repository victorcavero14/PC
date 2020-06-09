package Parte1IncrementosYDecrementos;

public class MonitorHilos{
	
	//private int _s = 0;
	//private final int _max  = 1; // Un hilo cada vez como si fuera un lock
	
	/*
	 public Sincronizacion(int max){
		_max = max;
	  }
	*/

	public MonitorHilos() {}
	
	public synchronized void incrementaValor(A a)
	{
		a.incrementa();
		System.out.println("a = " + a.get_var());
	}
	 
	public synchronized void decrementaValor(A a)
	{
		a.decrementa();
		System.out.println("a = " + a.get_var());
	}
	
	/*
	public synchronized void V() {
		
	    while(_s == _max) // bucle para evitar notify erroneos 
	    {
	    	try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    
	    _s++;
	    notifyAll();
	  }

	public synchronized void P() {
	    while(_s == 0)
	    {
	    	try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	   
	    _s--;
	    notifyAll();
	  }
	  */
}
