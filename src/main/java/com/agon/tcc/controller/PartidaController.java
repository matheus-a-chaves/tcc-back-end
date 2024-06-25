package com.agon.tcc.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

import com.agon.tcc.dto.AgendaDTO;
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
	
	@PostMapping("/equipe/{idEquipe}")
	public ResponseEntity<List<AgendaDTO>> encontrarPartidasPorData(@RequestBody Map<String, String> date, @PathVariable Long idEquipe) {
		try {
			String data = date.get("data");
			List<AgendaDTO> agendasDTO = this.partidaService.encontrarPartidasPorData(data, idEquipe);
			return ResponseEntity.ok().body(agendasDTO);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/amistosos/equipe/{idEquipe}")
	public ResponseEntity<List<AgendaDTO>> findAmistososByEquipe(@PathVariable Long idEquipe) {
		try {
			List<AgendaDTO> agendasDTO = this.partidaService.findAmistososByEquipe(idEquipe);
			return ResponseEntity.ok().body(agendasDTO);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}
	
//	@GetMapping("/campeonato/{id}")
//	public ResponseEntity<List<PartidaDTO>> findByCampeonato(@PathVariable Long id) {
//		List<PartidaDTO> partidasDTO = this.partidaService.findByCampeonato(id);
//		return ResponseEntity.ok().body(partidasDTO);
//	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody PartidaDTO partidaDTO) {
        partidaService.create(partidaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        		.path("/agon/partidas/{id}")
        		.buildAndExpand(partidaDTO.id())
        		.toUri();
        return ResponseEntity.created(uri).build();
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody PartidaDTO partidaDTO, @PathVariable Long id) {
		this.partidaService.update(new PartidaDTO(id, partidaDTO.dataPartida(), partidaDTO.endereco(), partidaDTO.etapaCampeonato(), 
												  partidaDTO.grupo(), partidaDTO.dadosPartidas(), partidaDTO.amistoso(), partidaDTO.campeonato()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.partidaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
