package com.admin.service.impl;

import static java.util.Comparator.comparing;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.admin.dao.ExchangeRateDAO;
import com.admin.dto.ExchangeRateRequest;
import com.admin.dto.ExchangeRateResponse;
import com.admin.dto.ExchangeRateUpdateRequest;
import com.admin.exceptions.InvalidCurrencyException;
import com.admin.infrastructure.gateway.CurrencyConversion;
import com.admin.infrastructure.gateway.ExchangeRate;
import com.admin.infrastructure.gateway.ExchangeRateGateway;
import com.admin.service.ExchangeRateService;

import hu.akarnokd.rxjava2.math.MathObservable;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

	@Autowired
	private ExchangeRateDAO dao;

	@Autowired
	private List<ExchangeRateGateway> exchangeRateGateway;

	@Value("${application.use.api}")
	private Integer useApi;

	@Override
	public Single<String> create(ExchangeRateRequest exchangeRate) {
		return createExchangeRateRepository(exchangeRate);
	}

	private Single<String> createExchangeRateRepository(ExchangeRateRequest exchangeRate) {
		return Single.create(singleSubscriber -> {
			Optional<com.admin.model.ExchangeRate> element = dao
					.fromByNameFromCurrencyAndNameToCurrency(exchangeRate.getFrom(), exchangeRate.getTo());
			if (element.isPresent()) {
				log.info("Elemento ya existente.");
				singleSubscriber.onError(new EntityExistsException());
			} else {
				log.info("Elemento para guardar");
				dao.save(toExchangeRate(exchangeRate));
				singleSubscriber.onSuccess("Registro guardado.");
			}
		});
	}

	private com.admin.model.ExchangeRate toExchangeRate(ExchangeRateRequest exchangeRate) {
		com.admin.model.ExchangeRate modelExchangeRate = new com.admin.model.ExchangeRate();
		modelExchangeRate.setId(UUID.randomUUID().toString());
		modelExchangeRate.setNameFromCurrency(exchangeRate.getFrom());
		modelExchangeRate.setNameToCurrency(exchangeRate.getTo());
		modelExchangeRate.setValueExchangeRate(exchangeRate.getAmount());

		return modelExchangeRate;
	}

	@Override
	public void delete(com.admin.model.ExchangeRate exchangeRate) {
		dao.delete(exchangeRate);
	}

	@Override
	public Completable update(ExchangeRateUpdateRequest exchangeRate) {
		return updateExchangeRateRepository(exchangeRate);
	}

	private Completable updateExchangeRateRepository(ExchangeRateUpdateRequest updateRequest) {
		return Completable.create(completableSubscriber -> {
			Optional<com.admin.model.ExchangeRate> element = dao.findById(updateRequest.getId());
			if (element.isPresent()) {
				com.admin.model.ExchangeRate exRate = element.get();
				exRate.setNameFromCurrency(updateRequest.getFrom());
				exRate.setNameToCurrency(updateRequest.getTo());
				exRate.setValueExchangeRate(updateRequest.getAmount());
				dao.save(exRate);
				completableSubscriber.onComplete();
			} else {
				completableSubscriber.onError(new EntityNotFoundException());
			}
		});
	}

	@Override
	public Single<List<com.admin.model.ExchangeRate>> listAll() {
		return Single.create(singleSubscriber -> {
			List<com.admin.model.ExchangeRate> findAll = dao.findAll();

			if (findAll.isEmpty()) {
				log.info("Lista vacia");
				singleSubscriber.onError(new NoResultException());
			} else {
				singleSubscriber.onSuccess(dao.findAll());
			}
		});
	}

	@Override
	public com.admin.model.ExchangeRate findById(String id) {
		return dao.findById(id).orElse(new com.admin.model.ExchangeRate());
	}

	@Override
	public Single<ExchangeRateResponse> generateExchangeRate(ExchangeRateRequest request) {

		if (useApi == 1) {

			Observable<ExchangeRate> exchangeRateObservable = Observable.fromIterable(exchangeRateGateway)
					.flatMap(gateway -> gateway.getExchangeRate(CurrencyConversion
							.from(parseToCurrency(request.getFrom())).to(parseToCurrency(request.getTo())))
							.toObservable())
					.doOnNext(exchangeRate -> log.info("Got rate: " + exchangeRate.getAmount()));

			/**
			 * return MathObservable.max(exchangeRateObservable,
			 * comparing(ExchangeRate::getAmount)).singleOrError();
			 */
			return Single.create(singleSubscriber -> {
				BigDecimal exchangeRateTo = MathObservable
						.max(exchangeRateObservable, comparing(ExchangeRate::getAmount)).singleOrError().blockingGet()
						.getAmount();

				if (exchangeRateTo == null) {
					singleSubscriber.onError(new EntityNotFoundException());
				}

				ExchangeRateResponse response = new ExchangeRateResponse();
				response.setAmount(request.getAmount());
				response.setFrom(request.getFrom());
				response.setTo(request.getTo());
				response.setAmountExchangeRate(exchangeRateTo.multiply(request.getAmount()));
				response.setValueExchangeRate(exchangeRateTo);

				singleSubscriber.onSuccess(response);

			});
		}
		
		return Single.create(singleSubscriberDb -> {
			
			Optional<com.admin.model.ExchangeRate> element = dao.fromByNameFromCurrencyAndNameToCurrency(request.getFrom(), request.getTo());
			
			if (!element.isPresent()) {
				singleSubscriberDb.onError(new EntityNotFoundException());
			}

			ExchangeRateResponse response = new ExchangeRateResponse();
			response.setAmount(request.getAmount());
			response.setFrom(request.getFrom());
			response.setTo(request.getTo());
			response.setAmountExchangeRate(element.get().getValueExchangeRate().multiply(request.getAmount()));
			response.setValueExchangeRate(element.get().getValueExchangeRate());

			singleSubscriberDb.onSuccess(response);
		});
	}

	private Currency parseToCurrency(String stringCurrency) {
		Currency currency;
		try {
			currency = Currency.getInstance(stringCurrency);
		} catch (IllegalArgumentException ex) {
			String message = String.format("Given currency: '%s' is invalid.", stringCurrency);
			log.error(message, ex);
			throw new InvalidCurrencyException(message, ex);
		}
		return currency;
	}

}
