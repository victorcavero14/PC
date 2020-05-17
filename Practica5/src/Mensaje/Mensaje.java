package Mensaje;

public abstract class Mensaje {
	
	// IMPLENTAR VARIAS CLASES
	// Implentar su funcionalidad dependiendo del tipo del mensaje (Envio de informacion, comienzo del protocolo...)
	
	public abstract int getTipo();
	
	public abstract String getOrigen();
	
	public abstract String getDestino();
}
