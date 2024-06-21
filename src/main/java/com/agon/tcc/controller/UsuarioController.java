package com.agon.tcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.agon.tcc.service.UsuarioService;

@RestController
@Validated
@RequestMapping("/agon/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
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
	public ResponseEntity<Void> create(@RequestBody @Validated RegisterDTO data) {
		try {
			usuarioService.create(data);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/alterarsenha")
	public ResponseEntity<Void> alterarSenha(@RequestBody @Validated String data) {
		try {
			this.usuarioService.alterarSenha(data);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
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
