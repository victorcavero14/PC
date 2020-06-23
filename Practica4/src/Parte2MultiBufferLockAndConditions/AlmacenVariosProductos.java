package Parte2MultiBufferLockAndConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlmacenVariosProductos implements Almacen {
	
	private int _n; // Productos que son posibles almacenar en el almacen
	private volatile List<Producto> _productos;
	
	private volatile Lock _lock;
	private volatile Condition _prods;
	private volatile Condition _hueco;
	
	public AlmacenVariosProductos(int n)
	{
		_n = n;
		_productos = new ArrayList<Producto>();
		_lock = new ReentrantLock();
		_prods = _lock.newCondition();
		_hueco = _lock.newCondition();
	}

	@Override
	public void almacenar(List<Producto> productos) {	
		
		_lock.lock();
		
		while((_productos.size() + productos.size()) > _n) // bucle para notify erroneos
		{
			try {
				_hueco.await();
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
		
		_prods.signalAll();
		_lock.unlock();
	}

	@Override
	public List<Producto> extraer(int nProductos) {		
		
		_lock.lock();
		
		while( (_productos.size() - nProductos) < 0) // bucle para notify erroneos
		{
			try {
				_prods.await();
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
		
		_hueco.signalAll();
		_lock.unlock();
		
		return prod_copia;
	}

}
