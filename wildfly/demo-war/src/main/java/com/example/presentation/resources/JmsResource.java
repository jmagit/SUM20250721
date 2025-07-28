package com.example.presentation.resources;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.contracts.domain.distributed.FacturasCommand;
import com.example.contracts.domain.distributed.PedidosCommand;

import jakarta.ejb.EJB;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;

@RestController
public class JmsResource {
	@Autowired
//	@EJB(lookup = "java:global/demo-ejb/PedidosQueue!com.example.contracts.domain.distributed.PedidosCommand")
	PedidosCommand pedidosCommand;
	@Autowired
//	@EJB(lookup = "java:global/demo-ejb/FacturasQueue!com.example.contracts.domain.distributed.FacturasCommand")
	FacturasCommand facturasCommand;

	@GetMapping("/ejb/a-pedidos/a-send")
	public String sendPedido(@RequestParam(defaultValue = "tienda 1") String mensaje) {
		pedidosCommand.send(mensaje);
		return "SEND: " + mensaje;
	}
	
	@GetMapping("/ejb/a-pedidos/b-receive")
	public String receivePedido(@RequestParam(defaultValue = "1000") long timeoutMillis) {
		String mensaje = pedidosCommand.receive(timeoutMillis);
		return "RECIBE: " + (mensaje == null ? "none" : mensaje);
	}

	@GetMapping("/ejb/b-facturas/a-send")
	public String sendFacturas(@RequestParam(defaultValue = "tienda 1") String mensaje) {
		facturasCommand.send(mensaje);
		return "SEND: " + mensaje;
	}

	@GetMapping("/ejb/b-facturas/b-receive")
	public String receiveFacturas(@RequestParam(defaultValue = "1000") long timeoutMillis) {
		String mensaje = facturasCommand.receive(timeoutMillis);
		return "RECIBE: " + (mensaje == null ? "none" : mensaje);
	}

//	@Autowired
//	ConnectionFactory jmsConnectionFactory;
//	@Autowired
//	Queue peticionesQueue;
//
//	@GetMapping("/jms/pedidos/send")
//	public String sendJmsPedido(@RequestParam(defaultValue = "tienda 1") String mensaje) {
//		try (JMSContext context = jmsConnectionFactory.createContext()) {
//			JMSProducer producer = context.createProducer();
//			for (int i = 0; i < 10; i++) {
//				String body = "Pedido de las " + LocalDateTime.now() + " de " + mensaje;
//				producer.send(peticionesQueue, body);
//				System.out.println("Pedido enviado por " + mensaje + ": " + body);
//			}
//		} catch (Exception e) {
//			return e.getClass().getSimpleName() + ": " + e.getMessage();
//		}
//		return "SEND: " + mensaje;
//	}

	@Autowired
	JmsTemplate jms;

	@GetMapping("/jms/pedidos/a-send")
	public String sendJmsPedido(@RequestParam(defaultValue = "tienda 1") String mensaje) {
		jms.convertAndSend("peticiones", mensaje);
		return "SEND: " + mensaje;
	}

	@GetMapping("/jms/facturas/b-receive")
	public String receiveJmsFacturas(@RequestParam(defaultValue = "1000") long timeoutMillis) {
		jms.setReceiveTimeout(timeoutMillis);
		var mensaje = (String) jms.receiveAndConvert("respuestas");
		return "RECIBE: " + (mensaje == null ? "none" : mensaje);
	}

	record MessageDTO(String mensaje, LocalDateTime enviado) implements Serializable {}
	record MessageReceived(String mensaje, LocalDateTime enviado, LocalDateTime recibido) {}
	
	@GetMapping("/jms/saludos/a-send")
	public String sendSaludos(@RequestParam(defaultValue = "mundo") String mensaje) {
		jms.convertAndSend("saludos", new MessageDTO("Hola " + mensaje, LocalDateTime.now()));
		jms.convertAndSend("oyente", new MessageDTO(mensaje, LocalDateTime.now()));
		return "SEND: " + mensaje;
	}

	@GetMapping("/jms/saludos/b-receive")
	public MessageReceived receiveSaludos(@RequestParam(defaultValue = "1000") long timeoutMillis) {
		jms.setReceiveTimeout(timeoutMillis);
		var mensaje = (MessageDTO) jms.receiveAndConvert("saludos");
		if(mensaje == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return new MessageReceived(mensaje.mensaje(), mensaje.enviado(), LocalDateTime.now());
	}

	private MessageReceived lastReceived;
	
	@JmsListener(destination = "oyente")
	public void listenQueue(MessageDTO in) {
		lastReceived = new MessageReceived(in.mensaje(), in.enviado(), LocalDateTime.now());
		System.out.println("MENSAJE RECIBIDO: " + in);
	}
	
	@GetMapping("/jms/saludos/c-oyente")
	public MessageReceived receiveOyente() {
		if(lastReceived == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return lastReceived;
	}

	record ChatDTO(String mensaje, String de, LocalDateTime enviado) implements Serializable {}

	@GetMapping("/jms/chat/a-send")
	public String sendChat(@RequestParam(defaultValue = "hola") String mensaje, @RequestParam(defaultValue = "tu") String de) {
		jms.convertAndSend("chat", new ChatDTO(mensaje, de, LocalDateTime.now()));
		return "SEND: " + mensaje;
	}

	private List<ChatDTO> tema = new ArrayList<>();
	
	@JmsListener(destination = "chat", containerFactory = "topicListenerContainerFactory" )
	public void listenTopic(ChatDTO in) {
		tema.add(0, in);
		lastReceived = new MessageReceived(in.mensaje(), in.enviado(), LocalDateTime.now());
		System.out.println("MENSAJE RECIBIDO: " + in);
	}

	@GetMapping("/jms/chat/b-list")
	public List<ChatDTO> listChat() {
		if(tema == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return tema;
	}


}
