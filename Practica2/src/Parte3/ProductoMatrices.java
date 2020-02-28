/*
 * Habria que modificarlo para poder determinar la matriz
 * mediante entrada. 
 */

package Parte3;

public class ProductoMatrices {
	
	private int [][] _mat1;
	private int [][] _mat2;
	private int [][] _resul;
	
	public ProductoMatrices()
	{
		_mat1 = new int[3][3];
		_mat2 = new int[3][3];
		_resul = new int[3][3];
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				_mat1[i][j] = 7;
				_mat2[i][j] = 7;
			}
		}
	}
	
	public void set_resul(int i, int j, int value)
	{
		_resul[i][j] = value;
	}
	
	public int get_mat1(int i, int j) {
		return _mat1[i][j];
	}
	public int get_mat2(int i, int j) {
		return _mat2[i][j];
	}
	public int get_resul(int i, int j) {
		return _resul[i][j];
	}
	

	
}
