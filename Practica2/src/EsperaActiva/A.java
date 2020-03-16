package EsperaActiva;
public class A {
	
	public int _var = 0;
	public boolean incrementada = false;
	public boolean decrementada = true;

	public void decrementa()
	{
		_var--;
	}
	
	public void incrementa()
	{
		_var++;
	}
	
	public int get_var() 
	{
		return _var;
	}

	public boolean get_dec()
	{
		return decrementada;
	}

	public boolean get_inc()
	{
		return incrementada;
	}

	public void setIncrementada(boolean incrementada) {
		this.incrementada = incrementada;
	}
	public void setDecrementada(boolean decrementada) {
		this.decrementada = decrementada;
	}

}
