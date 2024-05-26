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

import com.agon.tcc.dto.EtapaCampeonatoDTO;
import com.agon.tcc.service.EtapaCampeonatoService;

@RestController
@Validated
@RequestMapping("agon/etapaCampeonato")
public class EtapaCampeonatoController {

	@Autowired
	private EtapaCampeonatoService etapaCampeonatoService;
	
	@GetMapping
	public ResponseEntity<List<EtapaCampeonatoDTO>> findAll() {
		List<EtapaCampeonatoDTO> etapasCampeonatos = this.etapaCampeonatoService.findAll();
		return ResponseEntity.ok().body(etapasCampeonatos);
	}
	
	@GetMapping("/campeonato/{id}")
	public ResponseEntity<List<EtapaCampeonatoDTO>> findByCampeonato(@PathVariable Long id) {
		List<EtapaCampeonatoDTO> etapasCampeonato = this.etapaCampeonatoService.findByCampeonato(id);
		return ResponseEntity.ok().body(etapasCampeonato);
	}
	
	@GetMapping("/{nomeEtapa}/campeonato/{id}")
	public ResponseEntity<EtapaCampeonatoDTO> findByEtapaCampeonato(@PathVariable String nomeEtapa, @PathVariable Long id) {
		EtapaCampeonatoDTO etapaCampeonato = this.etapaCampeonatoService.findByEtapaCampeonato(nomeEtapa, id);
		return ResponseEntity.ok().body(etapaCampeonato);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody EtapaCampeonatoDTO etapaCampeonatoDTO) {
        etapaCampeonatoService.create(etapaCampeonatoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        		.path("/agon/etapaCampeonato/{id}")
        		.buildAndExpand(etapaCampeonatoDTO.id())
        		.toUri();
        return ResponseEntity.created(uri).build();
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody EtapaCampeonatoDTO etapaCampeonatoDTO, @PathVariable Long id) {
		this.etapaCampeonatoService.update(new EtapaCampeonatoDTO(id, etapaCampeonatoDTO.nomeEtapa(), etapaCampeonatoDTO.campeonato()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.etapaCampeonatoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
