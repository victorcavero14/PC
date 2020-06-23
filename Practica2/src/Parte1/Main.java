package Parte1;

import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

	public static void main(String[] args) {

		AtomicBoolean lock = new AtomicBoolean(false);
		int N = 10000; // numero de operaciones cada proceso

		A a = new A();
		
		HiloIncrementador hiloInc = new HiloIncrementador("Hilo incrementador 1", a, N, lock);
		hiloInc.start();
		
		HiloDecrementador hiloDec = new HiloDecrementador("Hilo decrementador 1", a, N, lock);
		hiloDec.start();

		try {
			hiloInc.join();
			hiloDec.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Hilo MAIN : todos los demas hilos han terminado.");
		System.out.println("a = " + a.get_var());

	}
}
