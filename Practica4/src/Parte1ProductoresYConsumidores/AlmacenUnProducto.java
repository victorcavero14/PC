package Parte1ProductoresYConsumidores;

public class AlmacenUnProducto implements Almacen {
	
	private volatile Producto _producto;
	
	public AlmacenUnProducto()
	{
		_producto = null;
	}

	@Override
	public synchronized void almacenar(Producto producto) {
		
		while(_producto != null) // bucle para evitar notify erroneos
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
		
		_producto = new Producto(producto); // En el caso de que almacenemos otra informacion del producto
		System.out.println(Thread.currentThread().getName() + " almacena: " + _producto.toString());
		
		notifyAll();
	}

	@Override
	public synchronized Producto extraer() {		
		
		while(_producto == null) // bucle para evitar notify erroneos
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Producto prod = new Producto(_producto);
		_producto = null;
		System.out.println(Thread.currentThread().getName() + " extrae: " + prod.toString());
		notifyAll();
		
		return prod;
	}

}
