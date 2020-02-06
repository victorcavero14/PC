/*
 * Comparticion de memoria entre hilos:
 * 
 * En las lecturas no hay problema por lo que no se producen
 * salidas erroneas. Cosa que en las escrituras si ocurre.
 * 
 */

package Parte3;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		
		int N = 3; //Debe ser del tam de la matriz
		long T = 0;
		
		ProductoMatrices pm = new ProductoMatrices();
		List <Hilo> listahilosInc= new ArrayList<Hilo>();
		
		for(int i = 0; i < N; i++)
		{
			Hilo hiloInc = new Hilo("hilo" + i, T, pm, i);
			listahilosInc.add(hiloInc);
		}
	
		for(Hilo hilo : listahilosInc) hilo.start();

		for(Hilo hilo : listahilosInc)
		{
			try {
				hilo.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Hilo MAIN : todos los demas hilos han terminado.");
		System.out.println("");
		
		String resultado = "";

		for(int i = 0; i < 3; i++)
		{
			for(int j= 0; j < 3; j++)
			{
				resultado += pm.get_resul(i, j) + " ";
			}
			System.out.println(resultado);
			resultado = "";
			System.out.println("----------");
		}
	}

}
