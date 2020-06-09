package Parte2MultiBufferSynchronized;

import java.util.List;

public interface Almacen {
	/**
	* Almacena (como ultimo) uno o varios productos en el almacen. Si no hay
	* hueco el proceso que ejecute el m´etodo bloquear´a hasta que lo
	* haya.
	*/
	public void almacenar(List<Producto> productos);
	/**
	* Extrae los primeros productos disponible. Si no hay productos el
	* proceso que ejecute el metodo bloqueara hasta que se almacene un
	* dato.
	*/
	public List<Producto> extraer(int nProductos);

}
