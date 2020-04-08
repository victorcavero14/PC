public class LockRompeEmpate {

    volatile int in[]; // etapa en la que se encuentra cada proceso i
    volatile int last[]; // identificador del proceso que llego ultilmo a la etapa i
    final int N;

    
    public LockRompeEmpate(int n)
    {
    	N = n;
    	in = new int[n+1];
    	last = new int[n+1];
    }
    
    public void takeLock(int i) {
    	for(int j = 1; j <= N; j++)
    	{
    		in[i] = j;
    		last[j] = i;
    		for(int k = 1; k <= N; k++)
    		{
    			if(k!= i)
    			{
    				while((in[k] >= in[i]) && (last[j] == i)); 
    			}
    		}
    	}
    }

    public void releaseLock(int i) {
    	in[i] = -1;
    }

}