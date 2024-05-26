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

import com.agon.tcc.dto.DadosPartidaDTO;
import com.agon.tcc.service.DadosPartidaService;

@RestController
@Validated
@RequestMapping("agon/dadosPartidas")
public class DadosPartidaController {

	@Autowired
	private DadosPartidaService dadosPartidaService;
	
	@GetMapping
	public ResponseEntity<List<DadosPartidaDTO>> findAll() {
		List<DadosPartidaDTO> dadosPartidas = this.dadosPartidaService.findAll();
		return ResponseEntity.ok().body(dadosPartidas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DadosPartidaDTO> findById(@PathVariable Long id) {
		DadosPartidaDTO dadosPartida = this.dadosPartidaService.findById(id);
		return ResponseEntity.ok().body(dadosPartida);
	}
	
	@GetMapping("/equipe/{id}")
	public ResponseEntity<List<DadosPartidaDTO>> findByEquipe(@PathVariable Long id) {
		List<DadosPartidaDTO> dadosPartidas = this.dadosPartidaService.findByEquipe(id);
		return ResponseEntity.ok().body(dadosPartidas);
	}
	
	@GetMapping("/partida/{id}")
	public ResponseEntity<List<DadosPartidaDTO>> findByPartida(@PathVariable Long id) {
		List<DadosPartidaDTO> dadosPartidas = this.dadosPartidaService.findByPartida(id);
		return ResponseEntity.ok().body(dadosPartidas);
	}
	
	@GetMapping("/equipe/{equipeId}/partida/{partidaId}")
	public ResponseEntity<DadosPartidaDTO> findByEquipePartida(@PathVariable Long equipeId, @PathVariable Long partidaId) {
		DadosPartidaDTO dadosPartida = this.dadosPartidaService.findByEquipePartida(equipeId, partidaId);
		return ResponseEntity.ok().body(dadosPartida);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody DadosPartidaDTO dadosPartidaDTO) {
        dadosPartidaService.create(dadosPartidaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        		.path("/agon/dadosPartida/{id}")
        		.buildAndExpand(dadosPartidaDTO.id())
        		.toUri();
        return ResponseEntity.created(uri).build();
    }
	
	@PutMapping("/equipe/{equipeId}/partida/{partidaId}")
	public ResponseEntity<Void> update(@RequestBody DadosPartidaDTO dadosPartidaDTO, @PathVariable Long equipeId, @PathVariable Long partidaId) {
		this.dadosPartidaService.update(new DadosPartidaDTO(dadosPartidaDTO.id(), dadosPartidaDTO.placar(), dadosPartidaDTO.qtdeCartaoVermelho(), dadosPartidaDTO.qtdeCartaoAmarelo(), 
															dadosPartidaDTO.penaltis(), dadosPartidaDTO.equipeId(), dadosPartidaDTO.partidaId()), partidaId, partidaId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.dadosPartidaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
