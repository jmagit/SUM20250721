import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {

	public static void main(String[] args) {
        try {
            // Crea un registro RMI en el puerto por defecto (1099)
            // Si el rmiregistry ya está corriendo, puedes usar LocateRegistry.getRegistry()
//            Registry registro = LocateRegistry.createRegistry(1099); // Puerto por defecto
            Registry registro = LocateRegistry.getRegistry(1099); // Puerto por defecto
            var servidor = new CortesiaServiceImpl();
            registro.rebind("CortesiaService", servidor);
            System.out.println("Servidor RMI listo.");
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
	}

}
