import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket {

	int number = 1;
	int next = 1;
    volatile AtomicInteger turn[];
    final int N;

    
    public LockTicket(int n)
    {
    	N = n;
    	turn = new AtomicInteger[n+1];
    	for(int i = 0; i <= n; i++)
    	{
    		turn[i] = new ;
    	}
    }
    
    public void takeLock(int i)
    {
    	turn[i] = 
    }

    public void releaseLock(int i)
    {
    	// no hace falta ya que solo un proceso a la vez llega aquí (El que tiene el ticket)
    }

}