package com.admin.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRateResponse {

	private BigDecimal amount;
	private BigDecimal amountExchangeRate;
	private BigDecimal valueExchangeRate;
	private String from;
	private String to;
}
