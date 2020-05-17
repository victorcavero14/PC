
import java.util.ArrayList;
import java.util.List;

public class Usuario {
	public String _id;
	public String _ip;
	public List<String> _ficheros;
	
	public Usuario(String id, String ip, ArrayList<String> ficheros)
	{
		_id = id;
		_ip = ip;
		_ficheros = ficheros;
	}
}
