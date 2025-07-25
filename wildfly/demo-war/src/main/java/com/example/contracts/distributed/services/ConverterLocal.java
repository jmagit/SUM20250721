package com.example.contracts.distributed.services;

import java.math.BigDecimal;

public interface ConverterLocal {
	BigDecimal dollarToYen(BigDecimal dollars);
	BigDecimal yenToEuro(BigDecimal yen);
	BigDecimal dollarToYenLocal(BigDecimal dollars);
}
