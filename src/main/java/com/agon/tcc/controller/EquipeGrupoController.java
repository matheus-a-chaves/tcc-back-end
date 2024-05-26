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

import com.agon.tcc.dto.EquipeGrupoDTO;
import com.agon.tcc.service.EquipeGrupoService;

@RestController
@Validated
@RequestMapping("/agon/timesgrupo")
public class EquipeGrupoController {

	@Autowired
	private EquipeGrupoService equipeGrupoService;
	
	@GetMapping
	public ResponseEntity<List<EquipeGrupoDTO>> findAll() {
		List<EquipeGrupoDTO> equipesDTO = this.equipeGrupoService.findAll();
		return ResponseEntity.ok().body(equipesDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EquipeGrupoDTO> findById(@PathVariable Long id) {
		EquipeGrupoDTO equipeGrupoDTO = this.equipeGrupoService.findById(id);
		return ResponseEntity.ok().body(equipeGrupoDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody EquipeGrupoDTO equipeGrupoDTO) {
		this.equipeGrupoService.create(equipeGrupoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/timesgrupo/{id}")
				.buildAndExpand(equipeGrupoDTO.id())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody EquipeGrupoDTO equipeGrupoDTO, @PathVariable Long id) {
		this.equipeGrupoService.update(new EquipeGrupoDTO(id, equipeGrupoDTO.equipe(), equipeGrupoDTO.grupo(), equipeGrupoDTO.pontos(), 
														  equipeGrupoDTO.qtdJogos(), equipeGrupoDTO.vitorias(), equipeGrupoDTO.empates(), 
														  equipeGrupoDTO.derrotas(), equipeGrupoDTO.saldoGols()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.equipeGrupoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
