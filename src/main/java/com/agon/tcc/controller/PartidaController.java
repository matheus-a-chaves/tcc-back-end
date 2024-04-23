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

import com.agon.tcc.dto.PartidaDTO;
import com.agon.tcc.service.PartidaService;

@RestController
@Validated
@RequestMapping("/agon/partidas")
public class PartidaController {

	@Autowired
	private PartidaService partidaService;
	
	@GetMapping
	public ResponseEntity<List<PartidaDTO>> findAll() {
		List<PartidaDTO> partidasDTO = this.partidaService.findAll();
		return ResponseEntity.ok().body(partidasDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PartidaDTO> findById(@PathVariable Long id) {
		PartidaDTO partidaDTO = this.partidaService.findById(id);
		return ResponseEntity.ok().body(partidaDTO);
	}
	
	@GetMapping("/campeonato/{id}")
	public ResponseEntity<List<PartidaDTO>> findByCampeonatoId(@PathVariable Long id) {
		List<PartidaDTO> partidasDTO = this.partidaService.findByCampeonatoId(id);
		return ResponseEntity.ok().body(partidasDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody PartidaDTO partidaDTO) {
		this.partidaService.create(partidaDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/agon/campeonatos/{id}").buildAndExpand(partidaDTO.id()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody PartidaDTO partidaDTO, @PathVariable Long id) {
		this.partidaService.update(new PartidaDTO(id, partidaDTO.dataPartida(), partidaDTO.endereco(), null, partidaDTO.placarA(), partidaDTO.placarB()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.partidaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
