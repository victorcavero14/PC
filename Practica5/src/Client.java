import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		// ejemplo socket pidiendo el tiempo a server
		
		if(args.length < 2) return;
		
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		try  {
			Socket socket = new Socket(hostname, port);
			InputStream inputC = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputC));
			String time = reader.readLine();
			OutputStream outputC = socket.getOutputStream();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}
