package ImplementacionesLocks;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket implements Lock{

	private volatile AtomicInteger number;
	private volatile AtomicInteger next;
    private volatile AtomicInteger turn[];
    
    public LockTicket(int n)
    {
    	number = new AtomicInteger(1);
    	next = new AtomicInteger(1);
    	
    	turn = new AtomicInteger[n+1];
    	for(int i = 1; i <= n; i++)
    	{
    		turn[i] = new AtomicInteger();
    	}
    }
    
    public void takeLock()
    {
    	int i = Integer.parseInt(Thread.currentThread().getName());	
    	
    	turn[i].set(number.getAndIncrement());
    	
    	while(turn[i].get() != next.get());	
    }

    public void releaseLock()
    {
    	next.incrementAndGet();
    	// no hace falta mas ya que solo un proceso a la vez llega aqui (El que tiene el ticket)
    }

}