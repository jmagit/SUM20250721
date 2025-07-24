package com.example.contracts.distributed.services;

import jakarta.ejb.Remote;
import jakarta.ejb.Remove;

@Remote
public interface CounterRemote {

	// Increment and return the number of hits
	int getHits();

	// Error
	@Remove
	void remove();

}