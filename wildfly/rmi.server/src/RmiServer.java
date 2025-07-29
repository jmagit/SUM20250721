import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

	public static void main(String[] args) {
        try {
            // Crea un registro RMI en el puerto por defecto (1099)
            Registry registro = LocateRegistry.createRegistry(1099); // Puerto por defecto
            // Si el rmiregistry ya está corriendo, puedes usar LocateRegistry.getRegistry()
            // Registry registro = LocateRegistry.getRegistry(1099); // Puerto por defecto
            var servicio = new CortesiaServiceImpl();
            var stub = (CortesiaService) UnicastRemoteObject.exportObject((CortesiaService) servicio, 0);
            registro.rebind("CortesiaService", stub);
            System.out.println("Servidor RMI listo.");
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
	}

}
