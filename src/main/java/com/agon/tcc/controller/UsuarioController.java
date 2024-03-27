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

import com.agon.tcc.model.Usuario;
import com.agon.tcc.service.UsuarioService;

@RestController
@Validated
@RequestMapping("/agon/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> usuarios = this.usuarioService.findAll();
		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Long id) {
		Usuario usuario = this.usuarioService.findById(id);
		return ResponseEntity.ok().body(usuario);
	}
	
//	@PostMapping
//	public ResponseEntity<Void> create(@RequestBody Usuario usuario) {
//		this.usuarioService.create(usuario);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//				.path("/agon/usuarios/{id}")
//				.buildAndExpand(usuario.getId())
//				.toUri();
//		return ResponseEntity.created(uri).build();
//	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Usuario usuario, @PathVariable Long id) {
		usuario.setId(id);
		this.usuarioService.update(usuario);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
