package com.meatshopmanager.controller;

import com.meatshopmanager.dto.LoginRequestDTO;
import com.meatshopmanager.dto.LoginResponseDTO;
import com.meatshopmanager.dto.RegistrarRequestDTO;
import com.meatshopmanager.dto.RegistrarResponseDTO;
import com.meatshopmanager.model.Usuario;
import com.meatshopmanager.security.JwtService;
import com.meatshopmanager.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UsuarioService usuarioService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<RegistrarResponseDTO> registrar(@Valid @RequestBody RegistrarRequestDTO dto) {
        Usuario usuario = usuarioService.registrar(dto.getEmail(), dto.getSenha(), dto.getRole());
        RegistrarResponseDTO response = new RegistrarResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getRole()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        String token = jwtService.gerarToken(userDetails.getUsername(), role);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}