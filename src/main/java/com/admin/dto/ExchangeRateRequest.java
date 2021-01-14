package com.admin.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateRequest {

	private BigDecimal amount;
	private String from;
	private String to;
}
