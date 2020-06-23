package Parte2;
import java.util.concurrent.atomic.AtomicInteger;

public class LockRompeEmpate implements Lock{

    private volatile AtomicInteger[] in; // etapa en la que se encuentra cada proceso i
    private volatile AtomicInteger[] last; // identificador del proceso que llego ultilmo a la etapa i
    private int N;

    
    public LockRompeEmpate(int n)
    {
    	N = n; 
    	in = new AtomicInteger[n+1];
    	last = new AtomicInteger[n+1];

    	for(int i = 0; i < N; i++)
    	{
    		in[i] = new AtomicInteger();
    		last[i] = new AtomicInteger();
    	}
    }
    
    public void takeLock() {
    	int i = Integer.parseInt(Thread.currentThread().getName());
    	
    	for(int j = 0; j < N; j++)
    	{
    		in[i].set(j);
    		last[j].set(i);
    		for(int k = 0; k < N; k++)
    		{
    			if(k!= i)
    			{
    				while((in[k].get() >= in[i].get()) && (last[j].get() == i)); 
    			}
    		}
    	}
    }

    public void releaseLock() {
    	int i = Integer.parseInt(Thread.currentThread().getName());
    	in[i].set(-1);;
    }

}