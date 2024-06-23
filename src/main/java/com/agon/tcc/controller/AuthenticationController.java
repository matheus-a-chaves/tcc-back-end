package com.agon.tcc.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agon.tcc.dto.LoginDTO;
import com.agon.tcc.dto.UsuarioDTO;
import com.agon.tcc.model.Login;
import com.agon.tcc.repository.LoginRepository;
import com.agon.tcc.service.UsuarioService;
import com.agon.tcc.util.PasswordUtil;

@RestController
@Validated
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Validated LoginDTO data) {
		try {
			
			Optional<Login> loginOptional = loginRepository.findLogin(data.login());
	 		Login login = loginOptional.get();
	 		UsuarioDTO usuarioDTO = usuarioService.findById(login.getUsuario());
	 		
	 		String salt = usuarioDTO.salt();
	 		String senha = data.password();
	 		
	 		// Calcule o hash da senha fornecida com o salt
	 		String senhaCriptografada = PasswordUtil.criptografarPassword(senha);
	 		
	 		 // Compare com a senha armazenada no banco de dados
	 	    if (senhaCriptografada.equals(login.getSenha())) {
	 	    	return ResponseEntity.ok(usuarioDTO);
	 	    } else {
	 	    	return ResponseEntity.badRequest().body("Erro ao autenticar o usuário! Usuário ou senha inválidos.");
	 	    }
	 		
		} catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body("Erro ao autenticar o usuário!");
		}
	}
	
}
