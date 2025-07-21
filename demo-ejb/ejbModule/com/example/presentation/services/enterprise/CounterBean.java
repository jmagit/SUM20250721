package com.example.presentation.services.enterprise;

import java.io.Serializable;

import jakarta.ejb.Local;
import jakarta.ejb.Remove;
import jakarta.ejb.Singleton;

@Singleton
public class CounterBean implements Serializable {
    private int hits = 1;

    // Increment and return the number of hits
    public int getHits() {
        return hits++;
    }
    
    // Error
    @Remove
    public void remove() {
    }
}
