package com.example.contracts.distributed.services;

import javax.ejb.Remote;

@Remote
public interface LikesBeanRemote {

	// Increment and return the number of hits
	int getHits();

}