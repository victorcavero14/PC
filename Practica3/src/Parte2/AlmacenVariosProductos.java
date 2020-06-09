package Parte2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class AlmacenVariosProductos implements Almacen {
	
	private int _n; // Productos que son posibles almacenar en el almacen
	private volatile List<Producto> _productos;
	private Semaphore _vacio;
	private Semaphore _lleno;
	private Semaphore _sincro; // Sincroniza el acceso a la lista uno cada vez
	
	public AlmacenVariosProductos(int n)
	{
		_n = n;
		_sincro = new Semaphore(1);
		_vacio = new Semaphore(0);
		_lleno = new Semaphore(_n);
		_productos = new ArrayList<Producto>();
	}

	@Override
	public void almacenar(Producto producto) {		
		try {
			_lleno.acquire();
			_sincro.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread th = Thread.currentThread();
		Producto prod = new Producto(producto);
		_productos.add(prod);  // En el caso de que almacenemos otra informacion del producto
		System.out.println(th.getName() + " almacena: " + prod.toString() + " --- Productos en almacen: " + _productos.size());
		_sincro.release();
		
		_vacio.release();
	}

	@Override
	public Producto extraer() {		
		try {
			 _vacio.acquire();
			 _sincro.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread th = Thread.currentThread();
		Producto prod = _productos.get(_productos.size() - 1);
		Producto prod_copia = new Producto(prod);
		
		_productos.remove(prod);
		System.out.println(th.getName() + " extrae: "+ prod_copia.toString() + " --- Productos en almacen: " + _productos.size());
		_sincro.release();
		
		_lleno.release();
		
		return prod_copia;
	}

}
