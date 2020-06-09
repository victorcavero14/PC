package Parte1ProductoresYConsumidores;

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
	public synchronized void almacenar(Producto producto) {	
		
		while(_productos.size() == _n) // bucle para evitar notify erroneos
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
		
		Thread th = Thread.currentThread();
		Producto prod = new Producto(producto);
		_productos.add(prod);  // En el caso de que almacenemos otra informacion del producto
		System.out.println(th.getName() + " almacena: " + prod.toString() + " --- Productos en almacen: " + _productos.size());
		
		notifyAll();
	}

	@Override
	public synchronized Producto extraer() {		
		
		while(_productos.size() == 0) // bucle para evitar notify erroneos
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	

		Thread th = Thread.currentThread();
		Producto prod = _productos.get(_productos.size() - 1);
		Producto prod_copia = new Producto(prod);
		
		_productos.remove(prod);
		System.out.println(th.getName() + " extrae: "+ prod_copia.toString() + " --- Productos en almacen: " + _productos.size());
		
		notifyAll();
		
		return prod_copia;
	}

}
