package com.example.contracts.distributed.services;

import jakarta.ejb.Local;

@Local
public interface CalculadoraLocal {
	double suma(double a, double b);

	double resta(double a, double b);

	double multiplica(double a, double b);

	double divide(double a, double b);
}
