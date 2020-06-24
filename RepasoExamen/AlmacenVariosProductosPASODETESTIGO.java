package Parte2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class AlmacenVariosProductosPASODETESTIGO implements Almacen {

    private volatile List<Producto> _productos;

    private volatile int _n;

    private volatile Semaphore _writer;
    private volatile Semaphore _reader;
    private volatile Semaphore _sincro; // Sincroniza el acceso a la lista uno cada vez

    public AlmacenVariosProductosPASODETESTIGO(int n) {
        _n = n;

        _sincro = new Semaphore(1);
        _reader = new Semaphore(0);
        _writer = new Semaphore(0);
        _productos = new ArrayList<Producto>();
    }

    @Override
    public void almacenar(Producto producto) {

        try {
            _sincro.acquire();
            
            while( _n == _productos.size()) 
            {
                _sincro.release();
                _writer.acquire();
                _sincro.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		Thread th = Thread.currentThread();
		Producto prod = new Producto(producto);
		_productos.add(prod);  // En el caso de que almacenemos otra informacion del producto
        System.out.println(th.getName() + " almacena: " + prod.toString() + " --- Productos en almacen: " + _productos.size());
        
        _sincro.release();
        _reader.release();
	}

	@Override
	public Producto extraer() {

		try {
            _sincro.acquire();

            while(_productos.size() == 0) 
            {
                _sincro.release();
                _reader.acquire();
                _sincro.acquire();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		Thread th = Thread.currentThread();
		Producto prod = _productos.get(_productos.size() - 1);
		Producto prod_copia = new Producto(prod);
		
		_productos.remove(prod);
        System.out.println(th.getName() + " extrae: "+ prod_copia.toString() + " --- Productos en almacen: " + _productos.size());
        
		_sincro.release();	
		_writer.release();
		
		return prod_copia;
	}

}
