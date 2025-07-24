package com.example.presentation.services.enterprise;

import java.io.Serializable;

import com.example.contracts.distributed.services.CounterRemote;

import jakarta.ejb.Remove;
import jakarta.ejb.Singleton;

@Singleton
public class CounterBean implements Serializable, CounterRemote {
    private int hits = 1;

    // Increment and return the number of hits
    @Override
	public int getHits() {
        return hits++;
    }
    
    // Error
    @Override
	@Remove
    public void remove() {
    }
}
