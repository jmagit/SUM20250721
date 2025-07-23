package com.example.contracts.distributed.services;

import java.math.BigDecimal;

import javax.ejb.Local;

//@Local
public interface ConverterBeanLocal {
	BigDecimal dollarToYen(BigDecimal dollars);
	BigDecimal yenToEuro(BigDecimal yen);
	BigDecimal dollarToYenLocal(BigDecimal dollars);
}
