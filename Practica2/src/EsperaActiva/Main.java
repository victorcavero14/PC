package EsperaActiva;
import java.util.ArrayList;
import java.util.List;

public class Main {

	volatile int in[];
	volatile int last[];

	public static void main(String[] args) {

		int N = 8000;
		
		A a = new A();
		
		HiloIncrementador hiloInc = new HiloIncrementador("hiloIncrementador" + 1, a, N);
		HiloDecrementador hiloDec = new HiloDecrementador("hiloDecrementador"+ 1, a, N);

		hiloInc.start();
		hiloDec.start();

		try {
			hiloInc.join();
			hiloDec.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Hilo MAIN : todos los demas hilos han terminado.");
		System.out.println("a = " + a.get_var());
		
		
	}
// ej2 def class A con los datos compartidos y el thread tiene ref a A 
}
