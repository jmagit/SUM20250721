package com.example.contracts.distributed.services;

import java.math.BigDecimal;

import jakarta.ejb.Remote;

@Remote
public interface ConverterRemote {
	BigDecimal dollarToYen(BigDecimal dollars);
	BigDecimal yenToEuro(BigDecimal yen);
}
