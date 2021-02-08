package com.admin.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

	@Id
	private String id;
	private String nameFromCurrency;
	private String nameToCurrency;
	private BigDecimal valueExchangeRate;
	private String date;
	
}
