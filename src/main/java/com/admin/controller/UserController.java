package com.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.dto.JWTRequest;
import com.admin.dto.JWTResponse;
import com.admin.repository.UsuarioRepository;
import com.admin.security.service.JWTService;

@RestController
@RequestMapping("/api/v1")
@SuppressWarnings("unused")
public class UserController {
		
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/logindfgf/")
	public ResponseEntity<JWTResponse> saveUsuario(@AuthenticationPrincipal JWTRequest request) {
		JWTResponse response = new JWTResponse();
		/** IN PROGRESS */
		return ResponseEntity.ok(response);
	}

	/**@GetMapping("/users/")
	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}

	@GetMapping("/users/{username}")
	public Usuario getUsuario(@PathVariable String username) {
		return usuarioRepository.findByUsername(username);
	}*/
	
	/**@PostMapping("/signIn")
    public ResponseEntity<UserDto> signIn(@AuthenticationPrincipal UserDto user) {
        user.setToken(userAuthenticationProvider.createToken(user.getLogin()));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.signUp(user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId() + "/profile")).body(createdUser);
    }

    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal UserDto user) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }*/
}
