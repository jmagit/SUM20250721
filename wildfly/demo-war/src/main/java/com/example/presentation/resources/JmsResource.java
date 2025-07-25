package com.example.presentation.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.contracts.domain.distributed.FacturasCommand;
import com.example.contracts.domain.distributed.PedidosCommand;

@RestController
public class JmsResource {
	@Autowired
	PedidosCommand pedidosCommand;
	@Autowired
	FacturasCommand facturasCommand;
	
	@GetMapping("/ejb/pedidos/send")
	public String sendPedido(@RequestParam String mensaje) {
		pedidosCommand.send(mensaje);
		return "SEND: " + mensaje;
	}
	
	@GetMapping("/ejb/pedidos/receive")
	public String receivePedido(@RequestParam(defaultValue = "1000") long timeoutMillis) {
		String mensaje = pedidosCommand.receive(timeoutMillis);
		return "RECIBE: " + (mensaje == null ? "none" : mensaje);
	}
	
	@GetMapping("/ejb/facturas/send")
	public String sendFacturas(@RequestParam String mensaje) {
		facturasCommand.send(mensaje);
		return "SEND: " + mensaje;
	}
	
	@GetMapping("/ejb/facturas/receive")
	public String receiveFacturas(@RequestParam(defaultValue = "1000") long timeoutMillis) {
		String mensaje = facturasCommand.receive(timeoutMillis);
		return "RECIBE: " + (mensaje == null ? "none" : mensaje);
	}

}
