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

import com.agon.tcc.dto.AmistosoDTO;
import com.agon.tcc.service.AmistosoService;

@RestController
@Validated
@RequestMapping("/agon/amistosos")
public class AmistosoController {

	@Autowired
	private AmistosoService amistosoService;
	
	@GetMapping
	public ResponseEntity<List<AmistosoDTO>> findAll() {
		List<AmistosoDTO> amistososDTO = this.amistosoService.findAll();
		return ResponseEntity.ok().body(amistososDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AmistosoDTO> findById(@PathVariable Long id) {
		AmistosoDTO amistosoDTO = this.amistosoService.findById(id);
		return ResponseEntity.ok().body(amistosoDTO);
	}
		
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody AmistosoDTO amistosoDTO) {
		this.amistosoService.create(amistosoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/amistosos/{id}")
				.buildAndExpand(amistosoDTO.id())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody AmistosoDTO amistosoDTO, @PathVariable Long id) {
		this.amistosoService.update(new AmistosoDTO(amistosoDTO.id(), amistosoDTO.dataHorario(), amistosoDTO.modalidade(), amistosoDTO.partida(), amistosoDTO.equipes()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.amistosoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
