import java.io.Serializable;
import java.rmi.RemoteException;

public class CortesiaServiceImpl implements CortesiaService, Serializable {
	
	@Override
	public String saludar(String nombre) throws RemoteException {
		if (nombre == null || nombre.trim().isEmpty()) {
			nombre = "mundo";
		}
		return "Hola, " + nombre + "!";
	}
	
	@Override
	public String despedir(String nombre) throws RemoteException {
		if (nombre == null || nombre.trim().isEmpty()) {
			nombre = "mundo";
		}
		return "Adiós, " + nombre + "!";
	}
}
