package com.example.contracts.distributed.services;

import java.math.BigDecimal;

public interface ConverterRemote {
	BigDecimal dollarToYen(BigDecimal dollars);
	BigDecimal yenToEuro(BigDecimal yen);
}
