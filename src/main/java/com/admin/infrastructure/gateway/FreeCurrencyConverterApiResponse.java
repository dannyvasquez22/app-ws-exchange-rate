package com.admin.infrastructure.gateway;

import java.math.BigDecimal;

import com.admin.infrastructure.dto.ExchangeRateApiResponse;

public class FreeCurrencyConverterApiResponse implements ExchangeRateApiResponse {

	private final BigDecimal amount;

	public FreeCurrencyConverterApiResponse(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}
}
