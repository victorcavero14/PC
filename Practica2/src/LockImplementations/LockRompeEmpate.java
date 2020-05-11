package LockImplementations;
import java.util.concurrent.atomic.AtomicInteger;

public class LockRompeEmpate implements Lock{
	
	
	// Necesario usar AtomicInteger para que el array sea volatitle y para evitar problemas con el código de la pizarra he omitido
	// el 0 de los arrays. Sería más óptimo en espacio utilizarlo.

    static volatile AtomicInteger[] in; // etapa en la que se encuentra cada proceso i
    volatile AtomicInteger[] last; // identificador del proceso que llego ultilmo a la etapa i
    final int N;

    
    public LockRompeEmpate(int n)
    {
    	N = n; 
    	in = new AtomicInteger[n+1];
    	last = new AtomicInteger[n+1];

    	for(int i = 1; i <= N; i++)
    	{
    		in[i] = new AtomicInteger();
    		last[i] = new AtomicInteger();
    	}
    }
    
    public void takeLock() {
    	
    	Thread th = Thread.currentThread();
    	int i = Integer.parseInt(th.getName());
    	
    	for(int j = 1; j <= N; j++)
    	{
    		in[i].set(j);
    		last[j].set(i);
    		for(int k = 1; k <= N; k++)
    		{
    			if(k!= i)
    			{
    				while((in[k].get() >= in[i].get()) && (last[j].get() == i)); 
    			}
    		}
    	}
    }

    public void releaseLock() {
    	Thread th = Thread.currentThread();
    	int i = Integer.parseInt(th.getName());
    	in[i].set(-1);;
    }

}