package LockImplementations;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket implements Lock{

	AtomicInteger number;
	AtomicInteger next;
	
    volatile AtomicInteger turn[];
    
    final int N;

    
    public LockTicket(int n)
    {
    	number = new AtomicInteger(1);
    	next = new AtomicInteger(1);
    	
    	N = n;
    	
    	turn = new AtomicInteger[n+1];
    	for(int i = 1; i <= n; i++)
    	{
    		turn[i] = new AtomicInteger();
    	}
    }
    
    public void takeLock()
    {
    	Thread th = Thread.currentThread();
    	int i = Integer.parseInt(th.getName());
    	
    	
    	turn[i].set(number.getAndIncrement());
    	
    	while(turn[i].get() != next.get());
    	
    			
    }

    public void releaseLock()
    {
    	next.incrementAndGet();
    	// no hace falta ya que solo un proceso a la vez llega aqui (El que tiene el ticket)
    }

}