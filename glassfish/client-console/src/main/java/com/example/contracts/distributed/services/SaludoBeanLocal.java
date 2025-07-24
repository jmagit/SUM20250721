package com.example.contracts.distributed.services;

import javax.ejb.Local;

@Local
public interface SaludoBeanLocal {
	String getSaludo();
	String getSaludoInformal();

}
