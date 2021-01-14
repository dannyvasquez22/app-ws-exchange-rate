package com.admin.infrastructure.gateway;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ExchangeRate implements Serializable {

	private static final long serialVersionUID = -8646517870168351905L;

	private final BigDecimal amount;
	private final CurrencyConversion currencyConversion;

	ExchangeRate(BigDecimal amount, CurrencyConversion currencyConversion) {
		this.amount = amount;
		this.currencyConversion = currencyConversion;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public CurrencyConversion getCurrencyConversion() {
		return currencyConversion;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ExchangeRate that = (ExchangeRate) o;
		return amount.equals(that.amount) && currencyConversion.equals(that.currencyConversion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, currencyConversion);
	}
}
