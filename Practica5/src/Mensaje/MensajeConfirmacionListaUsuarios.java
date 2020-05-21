package Mensaje;

import java.util.List;

import Servidor.Usuario;

public class MensajeConfirmacionListaUsuarios extends Mensaje{
	
	private List<Usuario> _listaUsuarios;
	
    public MensajeConfirmacionListaUsuarios (String origen, String destino, List<Usuario> listaUsuarios)
    {
        super(origen,destino);
        _tipo = 5;
        _listaUsuarios = listaUsuarios;
    }
    
    public List<Usuario> get_listaUsuarios()
    {
    	return _listaUsuarios;
    }
    
}