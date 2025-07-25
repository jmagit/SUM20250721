package com.example.contracts.domain.distributed;

import java.util.Optional;

public interface SensoresEvent {

	void send(String text);

	Optional<String> receive(long timeoutMillis);

}