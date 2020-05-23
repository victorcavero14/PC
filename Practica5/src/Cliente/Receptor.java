package Cliente;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Receptor extends Thread{
	
	private String _nombreCliente;
	private Socket _socket;
	private InputStream _inputR;
	
    public Receptor(String _ip, int port, String nombreCliente)
    {
    	try {
			_socket = new Socket(_ip, port);
			_inputR = _socket.getInputStream();
			_nombreCliente = nombreCliente;
			
    	} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void run()
    {
    	try {
    		
    		BufferedInputStream in = new BufferedInputStream(_inputR);
    		try (DataInputStream d = new DataInputStream(in)) {
    		    String fileName = d.readUTF();
    		    Files.copy(d,Paths.get("./" + _nombreCliente + "/" + fileName));
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}