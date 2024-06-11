package com.agon.tcc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agon.tcc.dto.LoginDTO;
import com.agon.tcc.dto.RegisterDTO;
import com.agon.tcc.model.Login;
import com.agon.tcc.model.Usuario;
import com.agon.tcc.repository.LoginRepository;
import com.agon.tcc.repository.UsuarioRepository;
import com.agon.tcc.service.UsuarioService;
import com.agon.tcc.util.PasswordEncoderUtil;

@RestController
@Validated
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Validated LoginDTO data) {
		
		try {
			Optional<Login> loginOptional = loginRepository.findLogin(data.login());
	        if (!loginOptional.isPresent()) {
	            return ResponseEntity.badRequest().body("Erro ao autenticar usuário, login não encontrado.");
	        }

	        Login login = loginOptional.get();
	        
	         var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
	         var auth = this.authenticationManager.authenticate(usernamePassword);
	         
	 		 //return ResponseEntity.ok(new LoginResponseDTO(token));
	         return ResponseEntity.ok(usuarioService.findById(login.getUsuario()));
	         
		} catch(Exception ex) {
			return ResponseEntity.badRequest().body("Erro ao autenticar o usuário! Usuário ou senha inválidos.");
		}
	}
	
	@PostMapping("/register")
	@Transactional
	public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
		if(this.loginRepository.findByLogin(data.login()) != null) {
			return ResponseEntity.badRequest().build();
		}
		
		String passwordEncrypted = PasswordEncoderUtil.encryptPassword(data.password());
		
		Usuario usuario = new Usuario(data.nome(), data.cpf(), data.cnpj(), data.imagemPerfil(), data.bairro(), data.cep(), data.cidade(), data.estado(), data.numero(), data.rua());
		
		if(usuario.getCpf() != null) {
			usuario.setTipoUsuario(2);
			
		} else if(usuario.getCnpj() != null) {
			usuario.setTipoUsuario(1);
		}
		
		usuarioRepository.save(usuario);
		usuario = (usuario.getCpf() != null) ? usuarioRepository.findByCpf(usuario.getCpf()) : usuarioRepository.findByCnpj(usuario.getCnpj());
		
		Login login = new Login(data.login(), passwordEncrypted, usuario.getId());
		loginRepository.save(login);
		
		return ResponseEntity.ok().build();
	}
}
