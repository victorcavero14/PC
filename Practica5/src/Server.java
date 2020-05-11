import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length < 1) return;
		
		int port = Integer.parseInt(args[0]);
		try  {
			ServerSocket serversocket = new ServerSocket(port);
			
			while(true)
			{
				Socket socket = serversocket.accept();
				
				InputStream inputS = socket.getInputStream();
				OutputStream outputS = socket.getOutputStream();
				
				PrintWriter writer = new PrintWriter(outputS, true);
				writer.println("User-Agent: Simple Http Client");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	

	}

}
