package Parte1;

public class SemaphoreImplementation{
	private int _s = 0;
	private int _max  = 0;

	  public SemaphoreImplementation(int max){
		_max = max;
	  }

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
}
