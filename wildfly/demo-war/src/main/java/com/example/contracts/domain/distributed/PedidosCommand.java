package com.example.contracts.domain.distributed;

public interface PedidosCommand {

	void send(String text);

	String receive(long timeoutMillis);

}