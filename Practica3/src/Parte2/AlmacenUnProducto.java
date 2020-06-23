package Parte2;

import java.util.concurrent.Semaphore;

public class AlmacenUnProducto implements Almacen {
	
	private volatile Producto _producto;
	private volatile Semaphore _vacio;
	private volatile Semaphore _lleno;
	
	public AlmacenUnProducto()
	{
		_vacio = new Semaphore(0);
		_lleno = new Semaphore(1);
		_producto = null;
	}

	@Override
	public void almacenar(Producto producto) {		
		try {
			_lleno.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread th = Thread.currentThread();
		_producto = new Producto(producto); // En el caso de que almacenemos otra informacion del producto
		System.out.println(th.getName() + " almacena: " + _producto.toString());

		_vacio.release();
	}

	@Override
	public Producto extraer() {		
		try {
			 _vacio.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread th = Thread.currentThread();
		Producto prod = new Producto(_producto);
		System.out.println(th.getName() + " extrae: " + prod.toString());
		
		_producto = null;
		
		_lleno.release();
		
		return prod;
	}

}
