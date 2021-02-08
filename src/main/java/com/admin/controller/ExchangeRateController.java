package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.dto.ExchangeRateRequest;
import com.admin.dto.ExchangeRateResponse;
import com.admin.dto.ExchangeRateUpdateRequest;
import com.admin.model.ExchangeRate;
import com.admin.service.ExchangeRateService;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("api/exchange-rate")
@Slf4j
@AllArgsConstructor
public class ExchangeRateController {

	@Autowired
	private ExchangeRateService exchangeRateService;
	
	@PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<ExchangeRateResponse>> generateExchangeRate(@RequestBody ExchangeRateRequest exchangeRateRequest) {
		log.trace("Inicio generate exchange rate");
		return new ResponseEntity<>(exchangeRateService.generateExchangeRate(exchangeRateRequest), HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<String>> createExchangeRate(@RequestBody ExchangeRateRequest exchangeRateRequest) {
		log.trace("Inicio create exchange rate");
		return new ResponseEntity<>(exchangeRateService.create(exchangeRateRequest), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<List<ExchangeRate>>> getAll() {
		log.trace("Inicio getAll exchange rates BD");
		return new ResponseEntity<>(exchangeRateService.listAll(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<String>> upateExchangeRate(@RequestBody ExchangeRateUpdateRequest exchangeRateRequest) {
		log.trace("Inicio create exchange rate");
		return new ResponseEntity<>(exchangeRateService.update(exchangeRateRequest).subscribeOn(Schedulers.io())
                .toSingle(() -> "Registro actualizado"), HttpStatus.OK);
	}
	
}
