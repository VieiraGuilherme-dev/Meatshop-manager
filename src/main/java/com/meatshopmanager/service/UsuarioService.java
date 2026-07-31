package com.meatshopmanager.service;

import com.meatshopmanager.model.Role;
import com.meatshopmanager.model.Usuario;
import com.meatshopmanager.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrar(String email, String senhaTextoPuro, Role role) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        String senhaCriptografada = passwordEncoder.encode(senhaTextoPuro);
        Usuario usuario = new Usuario(email, senhaCriptografada, role);
        return usuarioRepository.save(usuario);
    }
}