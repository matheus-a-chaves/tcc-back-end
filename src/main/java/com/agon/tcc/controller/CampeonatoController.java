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
	
	@GetMapping("/atletica/{idAtletica}/modalidade/{idModalidade}")
    public ResponseEntity<List<CampeonatoDTO>> findByAtleticaAndModalidade(@PathVariable Long idAtletica, @PathVariable Long idModalidade) {
        List<CampeonatoDTO> campeonatosDTO = this.campeonatoService.findByUsuarioIdAndModalidadeId(idAtletica, idModalidade);
        return ResponseEntity.ok().body(campeonatosDTO);
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
	
	@PostMapping("/{idCampeonato}/adicionar")
	public ResponseEntity<Void> adicionarEquipe(@RequestBody Map<String, String> cnpjValue, @PathVariable Long idCampeonato) {
		String cnpjAtletica = cnpjValue.get("cnpj");
		try {
			this.campeonatoService.adicionarEquipe(cnpjAtletica, idCampeonato);
			return ResponseEntity.ok().build();
		} catch(Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/{idCampeonato}/usuario/{idUsuario}/remover")
	public ResponseEntity<Void> removerEquipe(@PathVariable Long idCampeonato, @PathVariable Long idUsuario) {
		try {
			this.campeonatoService.removerEquipe(idCampeonato, idUsuario);
			return ResponseEntity.ok().build();
		} catch(Exception ex) {
			return ResponseEntity.badRequest().build();
		}
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
	
	/*
	 * Endpoint de inicialização do Campeonato
	 */
	@PostMapping("/{id}/iniciar")
    public ResponseEntity<Void> iniciarCampeonato(@PathVariable Long id) throws Exception {
        campeonatoService.iniciarCampeonato(id);
        return ResponseEntity.ok().build();
    }
}
