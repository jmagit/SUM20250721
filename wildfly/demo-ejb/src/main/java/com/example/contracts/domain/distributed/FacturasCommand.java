package com.example.contracts.domain.distributed;

import jakarta.ejb.Remote;

@Remote
public interface FacturasCommand {

	void send(String text);

	String receive(long timeoutMillis);

}