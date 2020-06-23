package Parte2;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class LockBakery implements Lock {
	
	private volatile AtomicBoolean[] flag;
	private volatile AtomicInteger[] turn;
	private int N;
			
	public LockBakery(int n)
	{
		N = n;
		flag = new AtomicBoolean[n+1];
		turn = new AtomicInteger[n+1];
		for(int i = 0; i<N; i++)
		{
			flag[i] = new AtomicBoolean();
			turn[i] = new AtomicInteger();
		}
	}
	
    public void takeLock()
    {
    	int i = Integer.parseInt(Thread.currentThread().getName());
    	
    	flag[i].set(true);
    	turn[i].set(max(turn) + 1);
    	
    	for(int j = 0; j < N; j++)
    	{
    		while ((j != i) && flag[j].get() && ((turn[j].get() < turn[i].get()) || ((turn[j].get() == turn[i].get()) && j < i)));
    	}
    }

    public void releaseLock()
    {
    	int i = Integer.parseInt(Thread.currentThread().getName());
    	
    	flag[i].set(false);
    }
    
    
    private int max(AtomicInteger[] list)
    {
    	AtomicInteger max = new AtomicInteger();
    	    	
    	for(int i = 0; i<N; i++)
    	{
        	max.getAndAccumulate(turn[i].get(), Math::max);
    		//if(list[i].get() > max.get()) max = list[i];
    	}
    	
    	return max.get();
    }
    
    
    
}