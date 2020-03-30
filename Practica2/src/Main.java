import java.util.ArrayList;
import java.util.List;

public class Main {

	//volatile int in[];
	//volatile int last[];

	public static void main(String[] args) {

		int N = 10000;
		int M = 6;
		
		A a = new A();
		List<Thread> hi = new ArrayList<Thread>();
		List<Thread> hd = new ArrayList<Thread>();
		
		for(int i = 0; i < M; i++)
		{
			HiloIncrementador hiloInc = new HiloIncrementador("hiloIncrementador " + Integer.toString(i), a, N);
			hiloInc.start();
			hi.add(hiloInc);

			HiloDecrementador hiloDec = new HiloDecrementador("hiloDecrementador " + Integer.toString(i), a, N);
			hiloDec.start();
			hd.add(hiloDec);
		}

		try {
			for(int i = 0; i < M; i++)
			{
				hi.get(i).join();
				hd.get(i).join();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Hilo MAIN : todos los demas hilos han terminado.");
		System.out.println("a = " + a.get_var());
		
		
	}
// ej2 def class A con los datos compartidos y el thread tiene ref a A 
}
