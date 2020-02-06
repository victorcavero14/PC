package Parte2;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {

		// MEJORA cambiar las listas a List<Thread> . Lo malo que la pelea por el dato no se ve tan clara.

		int N = 500;
		long T = 0;
		
		A a = new A();
		List <HiloIncrementador> listahilosInc= new ArrayList<HiloIncrementador>();
		List <HiloDecrementador> listahilosDec= new ArrayList<HiloDecrementador>();
		
		for(int i = 0; i < N; i++)
		{
			HiloIncrementador hiloInc = new HiloIncrementador("hiloIncrementador" + i, T, a);
			HiloDecrementador hiloDec = new HiloDecrementador("hiloDecrementador"+ i, T, a);
			listahilosInc.add(hiloInc);
			listahilosDec.add(hiloDec);
		}
		
		
		for(HiloIncrementador hilo : listahilosInc) hilo.start();
		for(HiloDecrementador hilo : listahilosDec) hilo.start();

		
		for(HiloIncrementador hilo : listahilosInc)
		{
			try {
				hilo.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(HiloDecrementador hilo : listahilosDec)
		{
			try {
				hilo.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println("Hilo MAIN : todos los demas hilos han terminado.");
		System.out.println("a = " + a.get_var());
		
		
	}
// ej2 def class A con los datos compartidos y el thread tiene ref a A 
}
