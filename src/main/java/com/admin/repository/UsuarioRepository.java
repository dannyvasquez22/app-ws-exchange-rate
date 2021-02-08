package com.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByUsername(String username);
}