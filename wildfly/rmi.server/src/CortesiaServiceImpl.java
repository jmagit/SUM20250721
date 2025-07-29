public class CortesiaServiceImpl implements CortesiaService {
	
	@Override
	public String saludar(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			nombre = "mundo";
		}
		return "Hola, " + nombre + "!";
	}
	
	@Override
	public String despedir(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			nombre = "mundo";
		}
		return "Adiós, " + nombre + "!";
	}
}
