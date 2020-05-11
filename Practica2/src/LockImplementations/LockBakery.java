package LockImplementations;

import java.awt.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LockBakery implements Lock {
	
	public volatile AtomicInteger[] turn;
	public final int N;
			
	public LockBakery(int n)
	{
		N = n;
		turn = new AtomicInteger[n+1];
		for(int i = 1; i<=N; i++)
		{
			turn[i] = new AtomicInteger();
		}
	}
	
    public void takeLock()
    {
    	Thread th = Thread.currentThread();
    	int i = Integer.parseInt(th.getName());
    	
    	turn[i].set(1);
    	turn[i].set(max(turn).get() + 1);
    	
    	for(int j = 1; j <= N; j++)
    	{
    		if(j!=i) while(turn[j].get() != 0 && (turn[i].get() > turn[j].get()));
    	}
    }

    public void releaseLock()
    {
    	Thread th = Thread.currentThread();
    	int i = Integer.parseInt(th.getName());
    	
    	turn[i].set(0);
    }
    
    private AtomicInteger max(AtomicInteger list[]) // EL MAX DEBE SER UNA FUNCION INTEGRA QUE SE EJECUTE CON EXCLUSION MUTUA!!
    {
    	AtomicInteger max = new AtomicInteger();
    	for(int i = 1; i<=N; i++)
    	{
    		if(list[i].get() > max.get()) max = list[i];
    	}
    	
    	return max;
    }
    
}