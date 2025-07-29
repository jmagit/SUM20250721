import java.rmi.Remote;

public interface CortesiaService extends Remote {

	String saludar(String nombre);

	String despedir(String nombre);

}