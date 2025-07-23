package com.example;

import java.io.Serializable;

import jakarta.ejb.Remove;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;

@Stateful
//@SessionScoped
public class LikesBeanPropio implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private int hits = 1;

    // Increment and return the number of hits
	public int getHits() {
		System.err.println("getHits() called, current hits: " + hits);
        return hits++;
    }
    
	@Remove
    public void remove() {
    }
}