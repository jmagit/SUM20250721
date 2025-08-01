package com.example.contracts.distributed.services;

import java.math.BigDecimal;

import jakarta.ejb.Local;

@Local
public interface ConverterLocal {
	BigDecimal dollarToYen(BigDecimal dollars);
	BigDecimal yenToEuro(BigDecimal yen);
	BigDecimal dollarToYenLocal(BigDecimal dollars);
}
