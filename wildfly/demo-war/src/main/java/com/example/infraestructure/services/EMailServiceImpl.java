package com.example.infraestructure.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.contracts.application.EMailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Primary
public class EMailServiceImpl implements EMailService {
	@Value("${spring.mail.username:adm@example.com}")
	private String from;
	@Autowired
	private JavaMailSender mailSender; // Spring inyectará automáticamente la implementación configurada

	@Override
	@Async
	public void sendEmail(String to, String subject, String body) {
		System.out.println("🚀 [Hilo: %s] Iniciando envío de correo a: %s".formatted(Thread.currentThread().getName(), to));
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from); // Reemplaza con tu remitente configurado
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
		System.out.println("✅ [Hilo: %s] Correo enviado a %s (%s)".formatted(Thread.currentThread().getName(), to, subject));
	}

	@Override
	@Async
	public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) {
		System.out.println("🚀 [Hilo: %s] Iniciando envío de correo a: %s".formatted(Thread.currentThread().getName(), to));
		try {
			FileSystemResource file = new FileSystemResource(new File(attachmentPath));
			MimeMessage message = mailSender.createMimeMessage();
			// true = multipart message, false = single part message
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body); // true = HTML, false = plain text
			helper.addAttachment(file.getFilename(), file);
			mailSender.send(message);
			System.out.println("Correo MIME (HTML/Adjuntos) enviado a: " + to);
		} catch (MessagingException e) {
			throw new RuntimeException("Error al enviar el correo con adjunto", e);
		}
		System.out.println("✅ [Hilo: %s] Correo enviado a %s (%s)".formatted(Thread.currentThread().getName(), to, subject));
	}

	@Async
	public void sendMimeEmail(String to, String subject, String htmlBody, boolean isHtml) {
		System.out.println("🚀 [Hilo: %s] Iniciando envío de correo a: %s".formatted(Thread.currentThread().getName(), to));
		try {
//			MimeMessage message = mailSender.createMimeMessage();
//			// true = multipart message, false = single part message			
//			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//			helper.setFrom(from);
//			helper.setTo(to);
//			helper.setSubject(subject);
//			helper.setText(htmlBody, isHtml); // true = HTML, false = plain text
//			mailSender.send(message);
			mailSender.send(message -> {
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
				helper.setFrom(from);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(htmlBody, isHtml); // true = HTML, false = plain text
			});
			System.out.println("Correo MIME (HTML/Adjuntos) enviado a: " + to);
		} catch (Exception e) {
			throw new RuntimeException("Error al enviar el correo MIME", e);
		}
		System.out.println("✅ [Hilo: %s] Correo MIME (HTML/Adjuntos) enviado a %s (%s)".formatted(Thread.currentThread().getName(), to, subject));
	}

}
