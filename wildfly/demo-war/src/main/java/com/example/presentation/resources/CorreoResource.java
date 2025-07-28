package com.example.presentation.resources;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.services.NotificationService;
import com.example.contracts.domain.repositories.ActorRepository;
import com.example.core.domain.exceptions.NotFoundException;

@RestController
@RequestMapping("/correo")
public class CorreoResource {
	@Autowired
	private NotificationService srv;

	
	@GetMapping(path = "/saludo")
	public String enviar(@RequestParam(defaultValue = "usr@example.com") String to, 
			@RequestParam(defaultValue = "Usuario de ejemplo") String nombre) {
		System.out.println("🚀 [Hilo: %s] Iniciando /correo".formatted(Thread.currentThread().getName()));
		srv.sendWelcomeEmail(to, nombre);
		return "correo enviado a las %s".formatted(LocalDateTime.now().toString());
	}
	
	@GetMapping(path = "/attachment")
	public String enviarWithAttachment(@RequestParam(defaultValue = "usr@example.com") String to, 
			@RequestParam(defaultValue = "Hola") String subject,
			@RequestParam String file) {
		System.out.println("🚀 [Hilo: %s] Iniciando /correo".formatted(Thread.currentThread().getName()));
		srv.sendEmail(to, subject, "Este es un mensaje de prueba enviado desde el servicio de correo.", file);
		return "correo enviado a las %s".formatted(LocalDateTime.now().toString());
	}

	@Autowired
	ActorRepository dao;
	
	@GetMapping(path = "/{id}/query")
	public String enviar(@PathVariable int id, @RequestParam(defaultValue = "usr@example.com") String to) throws NotFoundException {
		System.out.println("🚀 [Hilo: %s] Iniciando /correo".formatted(Thread.currentThread().getName()));
		var item = dao.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el actor con ID: " + id));
		srv.sendEmail(to, "Actor " + item.getActorId(), "Detalles del actor %d: %s %s ".formatted(item.getActorId(), item.getFirstName(), item.getLastName()));
		return "correo enviado a las %s".formatted(LocalDateTime.now().toString());
	}
	
	@GetMapping(path = "/{id}/mime")
	public String enviarMime(@PathVariable int id, @RequestParam(defaultValue = "usr@example.com") String to) throws NotFoundException {
		System.out.println("🚀 [Hilo: %s] Iniciando /correo".formatted(Thread.currentThread().getName()));
		var item = dao.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el actor con ID: " + id));
		var body = """
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actores</title>
</head>
<body>
    <h1>Detalle</h1>
    <dl>
		<dt>Código</dt>
		<dd>%d</dd>
		<dt>Nombre</dt>
		<dd>%s %s</dd>
    </dl>
</body>
</html>
""".formatted(item.getActorId(), item.getFirstName(), item.getLastName());
		srv.sendMimeEmail(to, "Actor " + item.getActorId(), body, true);
		return "correo enviado a las %s".formatted(LocalDateTime.now().toString());
	}

}