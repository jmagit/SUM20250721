package com.example.contracts.domain.distributed;

import java.util.Optional;

import jakarta.ejb.Remote;

@Remote
public interface SensoresEvent {

	void send(String text);

	Optional<String> receive(long timeoutMillis);

}