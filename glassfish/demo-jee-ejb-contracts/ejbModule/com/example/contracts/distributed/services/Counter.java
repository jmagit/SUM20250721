package com.example.contracts.distributed.services;

import jakarta.ejb.Local;

@Local
public interface Counter {

	// Increment and return the number of hits
	int getHits();

	// Error
	void remove();

}