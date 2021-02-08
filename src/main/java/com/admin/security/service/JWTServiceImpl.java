package com.admin.security.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.admin.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTServiceImpl implements JWTService {

	public static final String SECRET = Base64Utils.encodeToString(Constants.MY_SECRET_KEY.getBytes()); // Codigo secreto que genera el JWT

	@Override
	public String create(Authentication auth) throws IOException {
		String username = ((User) auth.getPrincipal()).getUsername();

		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

		Claims claims = Jwts.claims();
		claims.put(Constants.AUTHORITIES, new ObjectMapper().writeValueAsString(roles));

		return Jwts.builder()
						   .setClaims(claims) // Roles del usuario
						   .setSubject(username) // Nombre del Usuario
						   .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()) //Firma del token
						   .setIssuedAt(new Date()) // Fecha de creacion del token
						   .setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME)).compact(); // Fecha de expiracion
	}

	@Override
	public boolean validate(String token) {
		try {
			getClaims(token);

			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public Claims getClaims(String token) {
		return Jwts.parser()
						    .setSigningKey(SECRET.getBytes()) // Clave secreta en bytes
						    .parseClaimsJws(resolve(token)).getBody(); // Datos generales del JWT
	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get(Constants.AUTHORITIES);

		/* return too instance :: Collection<? extends GrantedAuthority> authorities
		 * SimpleGrantedAuthorityMixin :: combinación creada para añadir la propiedad "authority" en el parámetro de entrada del constructor de la clase base: SimpleGrantedAuthority
		 * 		y el mismo constructor generaría una estructura JSON que será utilizado para la conversión de string a lista de authorities: SimpleGrantedAuthority.
		 * */
		return Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
	}

	@Override
	public String resolve(String token) {
		if (token != null && token.startsWith(Constants.BEARER)) {
			return token.replace(Constants.BEARER, "");
		}
		return null;
	}

}
