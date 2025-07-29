import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {

	public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.getRegistry("localhost", 1099); // Conéctate al servidor
            var servicio = (CortesiaService) registro.lookup("CortesiaService"); //Obtener el objeto remoto
            System.out.println("Respuesta del servidor: " + servicio.saludar("Mundo"));
            System.out.println("Respuesta del servidor: " + servicio.despedir("mundo"));
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.toString());
            e.printStackTrace();
        }
	}

}
