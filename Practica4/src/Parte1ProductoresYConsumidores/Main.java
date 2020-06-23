package Parte1ProductoresYConsumidores;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {

		int N = 10; // Numero de procesos de cada tipo (TOTAL 2*N)
		int PRODUCTOS = 10; // Productos generados y consumidos por los respectivos hilos
		int TAM_ALMACEN = 20; // Tamaño maximo de productos que puede albergar el almacen

		//Almacen almacen = new AlmacenUnProducto();
		Almacen almacen = new AlmacenVariosProductos(TAM_ALMACEN);
		List<Thread> lc = new ArrayList<Thread>();
		List<Thread> lp = new ArrayList<Thread>();

		for (int i = 0; i < N; i++) {
			String nombre = "Consumidor " + String.valueOf(i);
			Consumidor consumidor = new Consumidor(nombre, almacen, PRODUCTOS);
			consumidor.start();
			lc.add(consumidor);
		}
		
		for (int i = 0; i < N; i++)
		{
			String nombre = "Productor " + String.valueOf(i);
			Productor productor = new Productor(nombre, almacen, PRODUCTOS);
			productor.start();
			lp.add(productor);
		}

		try {
			for (int i = 0; i < N; i++) {
				lp.get(i).join();
				lc.get(i).join(); 
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("MAIN : La ejecucion de los hilos ha terminado correctamente");
	}

}
