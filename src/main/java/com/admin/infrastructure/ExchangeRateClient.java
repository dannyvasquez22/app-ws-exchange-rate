package com.admin.infrastructure;

import com.admin.infrastructure.dto.ExchangeRateApiResponse;
import com.admin.infrastructure.gateway.CurrencyConversion;

import io.reactivex.Single;

public interface ExchangeRateClient {

	Single<ExchangeRateApiResponse> getApiResponse(CurrencyConversion currencyConversion);
}
