package com.admin.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
}
