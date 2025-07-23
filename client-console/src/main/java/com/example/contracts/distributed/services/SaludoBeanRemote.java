package com.example.contracts.distributed.services;

import javax.ejb.Remote;

@Remote
public interface SaludoBeanRemote {
	String getSaludo();
	String getSaludoFormal();
}
