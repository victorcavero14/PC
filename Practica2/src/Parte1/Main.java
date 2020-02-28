package Parte1;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {

		List <Hilo> listahilos= new ArrayList<Hilo>();
		int N = 20;
		long T = 60;
		
		for(int i = 0; i < N; i++)
		{
			Hilo hilo = new Hilo("hilo" + i, T);
			hilo.start();
			
			listahilos.add(hilo);
		}
		
		for(Hilo hilo : listahilos)
		{
			try {
				hilo.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Hilo MAIN : todos los demas hilos han terminado.");
		
		
	}
// ej2 def class A con los datos compartidos y el thread tiene ref a A 
}
