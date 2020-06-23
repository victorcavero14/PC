package Parte2MultiBufferLockAndConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Productor extends Thread {
	private volatile Almacen _almacen;
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
				int prods = r.nextInt() % (_nProductos-i+1);
				
				if(prods > 0)
				{
					List<Producto> list = new ArrayList<Producto>();
					
					for(int j = 0; j < prods; j++)
					{
						list.add(new Producto());
					}
					
					_almacen.almacenar(list);
					i = i + prods;
				}
			}
		}
	}
}
