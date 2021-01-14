package com.admin.infrastructure.gateway;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.infrastructure.ExchangeRateClient;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ExchangeRateGatewayImpl implements ExchangeRateGateway {

	@Autowired
	private ExchangeRateClient connection;

	@Override
	public Single<ExchangeRate> getExchangeRate(CurrencyConversion currencyConversion) {
		return connection.getApiResponse(currencyConversion).subscribeOn(Schedulers.io())
				.map(response -> new ExchangeRate(response.getAmount(), currencyConversion))
//				.timeout(TimeUnit.MILLISECONDS, TimeUnit.values(), scheduler)
				.doOnError(ex -> log.error("Encountered an error while trying to retrieve an exchange rate.", ex))
				.onErrorReturnItem(new ExchangeRate(BigDecimal.ZERO, currencyConversion));
	}

}
