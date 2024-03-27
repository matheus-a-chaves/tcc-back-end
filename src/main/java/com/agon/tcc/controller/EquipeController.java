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

import com.agon.tcc.model.Equipe;
import com.agon.tcc.service.EquipeService;

@RestController
@Validated
@RequestMapping("/agon/equipes")
public class EquipeController {
	
	@Autowired
	private EquipeService equipeService;
	
	@GetMapping
	public ResponseEntity<List<Equipe>> findAll() {
		List<Equipe> equipes = this.equipeService.findAll();
		return ResponseEntity.ok().body(equipes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Equipe> findById(@PathVariable Long id) {
		Equipe equipe = this.equipeService.findById(id);
		return ResponseEntity.ok().body(equipe);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody Equipe equipe) {
		this.equipeService.create(equipe);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/equipes/{id}")
				.buildAndExpand(equipe.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Equipe equipe, @PathVariable Long id) {
		equipe.setId(id);
		this.equipeService.update(equipe);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.equipeService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
