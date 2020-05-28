package Cliente;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class Emisor extends Thread{
	
	private ServerSocket _serverSocket;
	private String _nombreArchivo;
	private String _usuario;
	
    public Emisor(String nombreArchivo, String usuario)
    {
		try {
			ServerSocket sSocketCliente = new ServerSocket(0);
			
			_serverSocket = sSocketCliente;
			_nombreArchivo = nombreArchivo;
			_usuario = usuario;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void run()
    {
    	try {
    		mandarArchivo(_serverSocket.accept());
    		
    		_serverSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void mandarArchivo(Socket socket)
    {
    	try 
    	{
    		File file  = new File("./" + _usuario + "/" + _nombreArchivo);
 
    		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
    		try (DataOutputStream d = new DataOutputStream(out)) {
    		    d.writeUTF(_nombreArchivo);
    		    Files.copy(file.toPath(), d);
    		}       
    	}
    	catch (IOException e){
    		e.printStackTrace();
    	}
    }
    
    public int get_port()
    {
    	return _serverSocket.getLocalPort();
    }

}