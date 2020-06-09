package Parte1ProductoresYConsumidores;

import java.util.Random;

public class Productor extends Thread {
	private Almacen _almacen;
	private int _nProductos; // Productos que va a producir el productor
	
	public Productor(String nombre, Almacen almacen, int nProductos)
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
			if(r.nextInt() % 5 == 0) // 20% prob de producir
			{
				Producto proc = new Producto();
				_almacen.almacenar(proc);
				i++;
			}
		}
	}
}
