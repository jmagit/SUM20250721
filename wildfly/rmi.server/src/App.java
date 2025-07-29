
public class App {

	public static void main(String[] args) {
        try {
            var servidor = new CortesiaServiceImpl();
            java.rmi.registry.Registry registro = java.rmi.registry.LocateRegistry.createRegistry(1099); // Puerto por defecto
            registro.rebind("CortesiaService", servidor);
            System.out.println("Servidor RMI listo.");
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
	}

}
