package Parte2MultiBufferLockAndConditions;

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
				int prods = r.nextInt() % (_nProductos-i+1);
				
				if(prods > 0)
				{
					_almacen.extraer(prods); // Extrae una cantidad aleatoria de productos
					
					i = i + prods;
				}
			}
		}
	}
}
