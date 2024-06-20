package com.agon.tcc.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agon.tcc.dto.LoginDTO;
import com.agon.tcc.dto.UsuarioDTO;
import com.agon.tcc.infra.security.TokenService;
import com.agon.tcc.model.Login;
import com.agon.tcc.repository.LoginRepository;
import com.agon.tcc.service.UsuarioService;

@RestController
@Validated
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Validated LoginDTO data) {
		
		try {
	        
	         var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
	         var auth = this.authenticationManager.authenticate(usernamePassword);
	         
	 		 var token = tokenService.generateToken((Login) auth.getPrincipal());
	 		 
	 		Optional<Login> loginOptional = loginRepository.findLogin(data.login());
	 		Login login = loginOptional.get();
	 		UsuarioDTO usuarioDTO = usuarioService.findById(login.getUsuario());
	        
	 		HttpHeaders headers = new HttpHeaders();
	 		headers.setBearerAuth(token);
	 		return new ResponseEntity<>(usuarioDTO, headers, HttpStatus.OK);
	 		
		} catch(Exception ex) {
			return ResponseEntity.badRequest().body("Erro ao autenticar o usuário! Usuário ou senha inválidos.");
		}
	}
	
}
