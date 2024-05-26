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

import com.agon.tcc.dto.GrupoDTO;
import com.agon.tcc.service.GrupoService;

@RestController
@Validated
@RequestMapping("/agon/grupos")
public class GrupoController {

	@Autowired
	private GrupoService grupoService;
	
	@GetMapping
	public ResponseEntity<List<GrupoDTO>> findAll() {
		List<GrupoDTO> grupoDTO = this.grupoService.findAll();
		return ResponseEntity.ok().body(grupoDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GrupoDTO> findById(@PathVariable Long id) {
		GrupoDTO grupoDTO = this.grupoService.findById(id);
		return ResponseEntity.ok().body(grupoDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody GrupoDTO grupoDTO/*, @RequestBody List<Long> idsEquipes*/) {
		this.grupoService.create(grupoDTO/*, idsEquipes*/);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/grupos/{id}")
				.buildAndExpand(grupoDTO.id())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody GrupoDTO grupoDTO, @PathVariable Long id) {
		this.grupoService.update(new GrupoDTO(id, grupoDTO.nome(), grupoDTO.totalJogos(), grupoDTO.campeonato(), grupoDTO.equipesGrupos(), grupoDTO.partidas()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.grupoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
