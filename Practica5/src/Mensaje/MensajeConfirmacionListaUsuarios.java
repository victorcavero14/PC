package Mensaje;

import java.util.ArrayList;
import java.util.List;

import Servidor.Usuario;

public class MensajeConfirmacionListaUsuarios extends Mensaje{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Usuario> _listaUsuarios;
	
    public MensajeConfirmacionListaUsuarios (String origen, String destino, List<Usuario> listaUsuarios)
    {
        super(origen,destino);
        _tipo = 5;
        _listaUsuarios = new ArrayList<Usuario>(listaUsuarios);
    }
    
    public List<Usuario> get_listaUsuarios()
    {
    	return _listaUsuarios;
    }
    
}