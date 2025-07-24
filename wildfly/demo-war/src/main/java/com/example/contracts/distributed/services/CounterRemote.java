package com.example.contracts.distributed.services;

public interface CounterRemote {

	// Increment and return the number of hits
	int getHits();

	// Error
	void remove();

}