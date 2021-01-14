package com.admin.service;

import java.util.List;

import com.admin.dto.ExchangeRateRequest;
import com.admin.dto.ExchangeRateResponse;
import com.admin.dto.ExchangeRateUpdateRequest;
import com.admin.model.ExchangeRate;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ExchangeRateService {
	
	Single<String> create(ExchangeRateRequest exchangeRate);
	void delete(ExchangeRate exchangeRate);
	Completable update(ExchangeRateUpdateRequest exchangeRate);
	Single<List<ExchangeRate>> listAll();
	ExchangeRate findById(String id);
	Single<ExchangeRateResponse> generateExchangeRate(ExchangeRateRequest request);
//	Single<com.admin.infrastructure.gateway.ExchangeRate> generateExchangeRate(ExchangeRateRequest request);

}
