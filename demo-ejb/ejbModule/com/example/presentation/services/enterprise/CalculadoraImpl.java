package com.example.presentation.services.enterprise;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.example.contracts.distributed.services.CalculadoraLocal;
import com.example.contracts.distributed.services.CalculadoraRemote;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

/**
 * Session Bean implementation class CalculadoraImpl
 */
@Stateless(name = "Calculadora", mappedName = "CalculadoraImpl")
@LocalBean
public class CalculadoraImpl implements CalculadoraRemote, CalculadoraLocal {
	@Override
	public double suma(double a, double b) { return roundIEEE754(a + b); }
	@Override
	public double resta(double a, double b) { return roundIEEE754(a - b); }
	@Override
	public double multiplica(double a, double b) { return roundIEEE754(a * b); }
	@Override
	public double divide(double a, double b) { 
		if(b == 0) {
			throw new IllegalArgumentException("/ by zero");
		}
		return roundIEEE754(a / b); 
	}
	
	private double roundIEEE754(double o) {
		return BigDecimal.valueOf(o)
				.setScale(16, RoundingMode.HALF_UP)
				.doubleValue();
	}
}
