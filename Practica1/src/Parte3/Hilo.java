package Parte3;

public class Hilo extends Thread{
	
	public ProductoMatrices _pm;
	public long _sleeptime;
	public int _fila;
	
	public Hilo(String string, long sleep, ProductoMatrices pm, int i) {
		
		super(string);
		
		_sleeptime = sleep;
		_pm = pm;
		_fila = i;
	}

	public void run()
	{
		//System.out.println("Mi id es: " + getId() + " y mi nombre: " + getName());
		
		int valor = 0;
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				valor += _pm.get_mat1(_fila, j) * _pm.get_mat2(j, i);
			}
			_pm.set_resul(_fila, i, valor);
			valor = 0;
		}
		
		
		/*try {
			sleep(_sleeptime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//System.out.println("Mi id es: " + getId() + " y mi nombre: " + getName());
		
	}
}
