package com.agon.tcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agon.tcc.dto.RegisterDTO;
import com.agon.tcc.dto.UsuarioDTO;
import com.agon.tcc.model.Login;
import com.agon.tcc.model.Usuario;
import com.agon.tcc.repository.LoginRepository;
import com.agon.tcc.service.UsuarioService;
import com.agon.tcc.util.Util;

@RestController
@Validated
@RequestMapping("/agon/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<UsuarioDTO> usuariosDTO = this.usuarioService.findAll();
		return ResponseEntity.ok().body(usuariosDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
		UsuarioDTO usuarioDTO = this.usuarioService.findById(id);
		return ResponseEntity.ok().body(usuarioDTO);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@RequestBody @Validated RegisterDTO data) {
		if(this.loginRepository.findByLogin(data.login()) != null) {
			return ResponseEntity.badRequest().build();
		}
		
		Usuario usuario = Util.createAndValidateTipoUsuario(data);
		
		String passwordEncrypted = new BCryptPasswordEncoder().encode(data.password());
		
		usuarioService.create(usuario);
		usuario = (usuario.getCpf() != null) ? usuarioService.findByCpf(usuario.getCpf()) : usuarioService.findByCnpj(usuario.getCnpj());
		
		Login login = new Login(data.login(), passwordEncrypted, usuario.getId());
		loginRepository.save(login);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Long id) {
		this.usuarioService.update(new UsuarioDTO(id, usuarioDTO.nome(), usuarioDTO.dataNascimento(), null , null, usuarioDTO.imagemPerfil(), 
													usuarioDTO.bairro(), usuarioDTO.cep(), usuarioDTO.cidade(), usuarioDTO.estado(), usuarioDTO.numero(), usuarioDTO.rua(), null));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}
	

}
