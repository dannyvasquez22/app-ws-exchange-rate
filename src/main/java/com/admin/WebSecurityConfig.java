package com.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.admin.security.filter.JWTAuthenticationFilter;
import com.admin.security.filter.JWTAuthorizationFilter;
import com.admin.security.service.JWTService;
import com.admin.service.impl.JpaUserDetailsServiceImpl;
import com.admin.utils.Constants;

@EnableGlobalMethodSecurity(/*securedEnabled = true, */prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JpaUserDetailsServiceImpl userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JWTService jwtService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/**
			 * 1. Se desactiva el uso de cookies
			 * 2. Se activa la configuración CORS con los valores por defecto
			 * 3. Se desactiva el filtro CSRF
			 * 4. Se indica que el login no requiere autenticación
			 * 5. Se indica que el resto de URLs esten securizadas
			 * 6. Se agrega los filtros que determinan la logica de securizacion en su etapa authentication & authorization
		*/

		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.cors().and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, Constants.URL_LOGIN).permitAll()
			/**.antMatchers("/api/**", "/login").permitAll()*/
			.anyRequest().authenticated().and()
			.addFilterBefore(new JWTAuthenticationFilter(authenticationManager(), jwtService), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), jwtService), UsernamePasswordAuthenticationFilter.class)
			;
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		/**     // Direct datasource
		build.jdbcAuthentication()
			 .dataSource(dataSource)
			 .passwordEncoder(passwordEncoder)
			 .usersByUsernameQuery("select username, password, enabled from users where username=?")
			 .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");
		*/
		
		build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		
		/**     // Inmemory
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		build.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		.withUser(users.username("danny").password("12345").roles("USER"));
		*/
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
