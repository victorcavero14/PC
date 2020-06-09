package Parte1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {

		int N = 10000; // numero de operaciones cada proceso
		int M = 12; // numero de procesos de un tipo (TOTAL PROCESOS 2*M)
		
		Semaphore sem = new Semaphore(1); // Actua como un LOCK

		A a = new A();
		List<Thread> hi = new ArrayList<Thread>();
		List<Thread> hd = new ArrayList<Thread>();

		for (int i = 1; i <= M; i++) {
			HiloIncrementador hiloInc = new HiloIncrementador(Integer.toString(i), a, N, sem);
			hiloInc.start();
			hi.add(hiloInc);
		}
		
		for (int i = M+1; i <= 2*M; i++)
		{
			HiloDecrementador hiloDec = new HiloDecrementador(Integer.toString(i), a, N, sem);
			hiloDec.start();
			hd.add(hiloDec);
		}

		try {
			for (int i = 0; i < M; i++) {
				hi.get(i).join();
				hd.get(i).join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Hilo MAIN : todos los demas hilos han terminado.");
		System.out.println("a = " + a.get_var());

	}
}
