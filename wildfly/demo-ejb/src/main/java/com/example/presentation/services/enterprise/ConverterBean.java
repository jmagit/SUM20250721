package com.example.presentation.services.enterprise;

import java.math.BigDecimal;

import com.example.contracts.distributed.services.ConverterLocal;
import com.example.contracts.distributed.services.ConverterRemote;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

/**
 * Session Bean implementation class ConverterBean
 */
@Stateless
@LocalBean
public class ConverterBean implements ConverterRemote, ConverterLocal {
	private final BigDecimal yenRate = new BigDecimal("104.34");
	private final BigDecimal euroRate = new BigDecimal("0.007");

	@Override
	public BigDecimal dollarToYen(BigDecimal dollars) {
		BigDecimal result = dollars.multiply(yenRate);
		return result.setScale(2, BigDecimal.ROUND_UP);
	}
	
	@Override
	public BigDecimal dollarToYenLocal(BigDecimal dollars) {
		return dollarToYen(dollars.add(new BigDecimal(0.5)));
	}

	@Override
	public BigDecimal yenToEuro(BigDecimal yen) {
		BigDecimal result = yen.multiply(euroRate);
		return result.setScale(2, BigDecimal.ROUND_UP);
	}
}
