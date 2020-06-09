package Parte1ProductoresYConsumidores;

import java.util.Random;

public class Consumidor extends Thread {
	private Almacen _almacen;
	private int _nProductos;
	
	// Para que los hilos consumidores acaben "_nProductos" , podria quitarse la variable y hacerlos while(true) 
	// pero nunca se llegaria al join en el Main
	
	public Consumidor(String nombre, Almacen almacen, int nProductos)
	{
		super(nombre);
		_almacen = almacen;
		_nProductos = nProductos;
	}
	
	public void run()
	{
		Random r = new Random();
		int i = 0;
		
		while(i < _nProductos)
		{
			if(r.nextInt() % 10 == 0) // 10% prob de consumir
			{
				_almacen.extraer();
				i++;
			}
		}
	}
}
