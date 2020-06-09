package Parte2MultiBufferSynchronized;

import java.util.ArrayList;
import java.util.List;

public class AlmacenVariosProductos implements Almacen {
	
	private int _n; // Productos que son posibles almacenar en el almacen
	private volatile List<Producto> _productos;
	
	public AlmacenVariosProductos(int n)
	{
		_n = n;
		_productos = new ArrayList<Producto>();
	}

	@Override
	public synchronized void almacenar(List<Producto> productos) {	
		
		while((_productos.size() + productos.size()) > _n) // bucle para evitar notify erroneos
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
		
		Thread th = Thread.currentThread();
		
		for(int i = 0; i < productos.size(); i++)
		{
			_productos.add(new Producto(productos.get(i)));
		}
		
		System.out.println(th.getName() + " almacena: " + productos.size() + " producto/s" + " --- Productos en almacen: " + _productos.size());
		
		notifyAll();
	}

	@Override
	public synchronized List<Producto> extraer(int nProductos) {		
		
		while( (_productos.size() - nProductos) < 0) // bucle para evitar notify erroneos
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	

		Thread th = Thread.currentThread();
		List<Producto> prod_copia = new ArrayList<Producto>();
		
		for(int i = 0; i < nProductos; i++)
		{
			Producto prod = _productos.get(_productos.size() - 1);
			prod_copia.add(new Producto(prod));
			_productos.remove(_productos.size() - 1);
		}
		
		
		System.out.println(th.getName() + " extrae: "+ nProductos + " producto/s" + " --- Productos en almacen: " + _productos.size());
		
		notifyAll();
		
		return prod_copia;
	}

}
