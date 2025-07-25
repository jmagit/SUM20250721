package com.example.contracts.domain.distributed;

public interface FacturasCommand {

	void send(String text);

	String receive(long timeoutMillis);

}