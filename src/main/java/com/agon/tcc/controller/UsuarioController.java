package com.agon.tcc.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<Void> create(@RequestBody UsuarioDTO usuarioDTO) {
		this.usuarioService.create(usuarioDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/usuarios/{id}")
				.buildAndExpand(usuarioDTO.id())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Long id) {
		this.usuarioService.update(new UsuarioDTO(id, usuarioDTO.nome(), usuarioDTO.cpf(), usuarioDTO.cnpj(), usuarioDTO.imagemPerfil(), usuarioDTO.endereco()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}
	

}
