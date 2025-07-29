import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CortesiaService extends Remote {

	String saludar(String nombre) throws RemoteException;

	String despedir(String nombre) throws RemoteException;

}