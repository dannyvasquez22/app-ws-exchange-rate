package com.admin.infrastructure.gateway;

import io.reactivex.Single;

public interface ExchangeRateGateway {

	Single<ExchangeRate> getExchangeRate(CurrencyConversion currencyConversion);
}
