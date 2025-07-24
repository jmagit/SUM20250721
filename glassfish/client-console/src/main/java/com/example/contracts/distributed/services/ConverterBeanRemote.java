package com.example.contracts.distributed.services;

import java.math.BigDecimal;

import javax.ejb.Remote;

@Remote
public interface ConverterBeanRemote {
	BigDecimal dollarToYen(BigDecimal dollars);
	BigDecimal yenToEuro(BigDecimal yen);
}
