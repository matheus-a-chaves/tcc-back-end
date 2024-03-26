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

import com.agon.tcc.model.Campeonato;
import com.agon.tcc.service.CampeonatoService;

@RestController
@Validated
@RequestMapping("/agon/campeonatos")
public class CampeonatoController {
	
	@Autowired
	private CampeonatoService campeonatoService;
	
	@GetMapping
	public ResponseEntity<List<Campeonato>> findAll() {
		List<Campeonato> campeonatos = this.campeonatoService.findAll();
		return ResponseEntity.ok().body(campeonatos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Campeonato> findById(@PathVariable Long id) {
		Campeonato campeonato = this.campeonatoService.findById(id);
		return ResponseEntity.ok().body(campeonato);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody Campeonato campeonato) {
		this.campeonatoService.create(campeonato);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/campeonatos/{id}")
				.buildAndExpand(campeonato.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Campeonato campeonato, @PathVariable Long id) {
		campeonato.setId(id);
		this.campeonatoService.update(campeonato);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.campeonatoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
