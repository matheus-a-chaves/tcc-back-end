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

import com.agon.tcc.dto.CampeonatoDTO;
import com.agon.tcc.service.CampeonatoService;

@RestController
@Validated
@RequestMapping("/agon/campeonatos")
public class CampeonatoController {
	
	@Autowired
	private CampeonatoService campeonatoService;
	
	@GetMapping
	public ResponseEntity<List<CampeonatoDTO>> findAll() {
		List<CampeonatoDTO> campeonatosDTO = this.campeonatoService.findAll();
		return ResponseEntity.ok().body(campeonatosDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CampeonatoDTO> findById(@PathVariable Long id) {
		CampeonatoDTO campeonatoDTO = this.campeonatoService.findById(id);
		return ResponseEntity.ok().body(campeonatoDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody CampeonatoDTO campeonatoDTO) {
		this.campeonatoService.create(campeonatoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/campeonatos/{id}")
				.buildAndExpand(campeonatoDTO.id())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody CampeonatoDTO campeonatoDTO, @PathVariable Long id) {
		this.campeonatoService.update(new CampeonatoDTO(id, campeonatoDTO.nome(), campeonatoDTO.quantidadeEquipes(), 
									  					campeonatoDTO.dataInicio(), campeonatoDTO.dataFim(), null, null, null, null));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.campeonatoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
