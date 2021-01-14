package com.admin.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.admin.model.ExchangeRate;

@Repository
public interface ExchangeRateDAO extends JpaRepository<ExchangeRate, String> {

	@Query("SELECT ex FROM ExchangeRate ex WHERE nameFromCurrency=:from AND nameToCurrency=:to")
	public Optional<ExchangeRate> fromByNameFromCurrencyAndNameToCurrency(@Param("from") String from, @Param("to") String to);
}
