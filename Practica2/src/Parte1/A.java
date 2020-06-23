package Parte1;

public class A {
	
	private volatile int _var = 0;

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

}
