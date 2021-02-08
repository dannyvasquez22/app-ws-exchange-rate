package com.admin.security.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityMixin { //NOSONAR

	@JsonCreator
	SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {}

}
