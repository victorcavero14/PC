
import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String _id;
	private String _ip;
	private List<String> _ficheros;
	
	public Usuario(String id, String ip, ArrayList<String> ficheros)
	{
		_id = id;
		_ip = ip;
		_ficheros = ficheros;
	}

	public String get_id()
	{
		return _id;
	}

	public String get_ip()
	{
		return _ip;
	}

	public List<String> get_ficheros()
	{
		return _ficheros;
	}

}
