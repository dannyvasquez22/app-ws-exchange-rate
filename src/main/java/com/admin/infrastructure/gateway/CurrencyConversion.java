package com.admin.infrastructure.gateway;

import java.io.Serializable;
import java.util.Currency;
import java.util.Objects;

public class CurrencyConversion implements Serializable {

	private static final long serialVersionUID = -2391194360836271059L;

	private final Currency from;
	private final Currency to;

	private CurrencyConversion(Currency from, Currency to) {
		this.from = from;
		this.to = to;
	}

	public Currency getFrom() {
		return from;
	}

	public Currency getTo() {
		return to;
	}

	public static CurrencyConversionBuilder from(Currency from) {
		return new CurrencyConversionBuilder(from);
	}

	public static class CurrencyConversionBuilder {

		private final Currency from;

		CurrencyConversionBuilder(Currency from) {
			this.from = from;
		}

		public CurrencyConversion to(Currency to) {
			return new CurrencyConversion(from, to);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CurrencyConversion that = (CurrencyConversion) o;
		return from.equals(that.from) && to.equals(that.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
}
