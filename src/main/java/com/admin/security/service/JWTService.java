package com.admin.security.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

public interface JWTService {

	public String create(Authentication auth) throws IOException; // Crear JWT
	public boolean validate(String token); // Validar JWT
	public Claims getClaims(String token); // Obtener datos generales del token (usuario, fecha creacion, fecha expiracion, etc)
	public String getUsername(String token); // Obtener nombre del usuario que creo el token
	public Collection<GrantedAuthority> getRoles(String token) throws IOException; // Obtener roles de usuario que creo el token
	public String resolve(String token); // Omitir prefijo "Bearer " y extraer solo token del response

}
