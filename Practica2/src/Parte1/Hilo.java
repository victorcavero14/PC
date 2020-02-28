package Parte1;

public class Hilo extends Thread {
	
	public long _sleeptime;
	
	public Hilo(String string, long sleep) {
		super(string);
		_sleeptime = sleep;
	}

	public void run()
	{
		System.out.println("Mi id es: " + getId() + " y mi nombre: " + getName());
		
		try {
			sleep(_sleeptime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Mi id es: " + getId() + " y mi nombre: " + getName());
	}
}
