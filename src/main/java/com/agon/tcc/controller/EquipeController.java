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

import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.dto.UsuarioDTO;
import com.agon.tcc.service.EquipeService;
import com.agon.tcc.service.UsuarioService;
import java.util.Map;
import jakarta.transaction.Transactional;

@RestController
@Validated
@RequestMapping("/agon/equipes")
public class EquipeController {
	
	@Autowired
	private EquipeService equipeService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<EquipeDTO>> findAll() {
		List<EquipeDTO> equipesDTO = this.equipeService.findAll();
		return ResponseEntity.ok().body(equipesDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EquipeDTO> findById(@PathVariable Long id) {
		EquipeDTO equipeDTO = this.equipeService.findById(id);
		return ResponseEntity.ok().body(equipeDTO);
	}
	
	@GetMapping("/{id}/jogadores")
	public ResponseEntity<List<UsuarioDTO>> findJogadoresByEquipe (@PathVariable Long id) {
		List<UsuarioDTO> usuariosDTO = this.usuarioService.findAllJogadoresByEquipe(id);
		return ResponseEntity.ok().body(usuariosDTO);
	}
	
	@GetMapping("/campeonato/{id}")
	public ResponseEntity<List<EquipeDTO>> findAllTimesByIdCampeonato (@PathVariable Long id) {
		List<EquipeDTO> equipesDTO = this.equipeService.findAllTimesByIdCampeonato(id);
		return ResponseEntity.ok().body(equipesDTO);
	}
	
	@GetMapping("/atletica/{id}")
	public ResponseEntity<List<EquipeDTO>> findAllTimesByIdAtletica (@PathVariable Long id) {
		List<EquipeDTO> equipesDTO = this.equipeService.findAllTimesByIdAtletica(id);
		return ResponseEntity.ok().body(equipesDTO);
	}
	
	@GetMapping("/jogador/{id}")
	public ResponseEntity<List<EquipeDTO>> findAllTimesByIdJogador (@PathVariable Long id) {
		List<EquipeDTO> equipesDTO = this.equipeService.findAllTimesByIdJogador(id);
		return ResponseEntity.ok().body(equipesDTO);
	}
	
	@PostMapping("/disponiveis/modalidade/{idModalidade}/atletica/{idAtletica}")
	public ResponseEntity<List<EquipeDTO>> findTimesDisponiveisAmistoso (@RequestBody Map<String, String> date, @PathVariable Long idModalidade, @PathVariable Long idAtletica) {
		try {
			String data = date.get("date");
			List<EquipeDTO> equipesDTO = this.equipeService.findTimesDisponiveisAmistoso(data, idModalidade, idAtletica);
			return ResponseEntity.ok().body(equipesDTO);
		} catch(Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/atletica/{id}")
	@Transactional
	public ResponseEntity<Void> create(@RequestBody EquipeDTO equipeDTO, @PathVariable Long id) {
		this.equipeService.create(equipeDTO, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/equipes/{id}")
				.buildAndExpand(equipeDTO.id())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("/{idEquipe}/atletica/{idAtletica}/jogador/adicionar")
	@Transactional
	public ResponseEntity<Void> adicionarJogador(@RequestBody Map<String, String> cpfValue, @PathVariable Long idEquipe, @PathVariable Long idAtletica) {
		String cpfJogador = cpfValue.get("cpf");
		if (this.equipeService.adicionarJogador(cpfJogador, idEquipe, idAtletica)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/{idEquipe}/atletica/{idAtletica}/jogador/{idJogador}/remover")
	@Transactional
	public ResponseEntity<Void> removerJogador(@PathVariable Long idJogador, @PathVariable Long idEquipe, @PathVariable Long idAtletica) {
		if (this.equipeService.removerJogador(idJogador,  idEquipe, idAtletica)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody EquipeDTO equipeDTO, @PathVariable Long id) {
//		this.equipeService.update(new EquipeDTO(id, equipeDTO.nome(), equipeDTO.imagem(), equipeDTO.modalidade(), equipeDTO.equipeGrupos(), equipeDTO.dadosPartidas()));
		this.equipeService.update(new EquipeDTO(id, equipeDTO.nome(), equipeDTO.imagem(), equipeDTO.modalidade()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.equipeService.delete(id);
		return ResponseEntity.noContent().build();
	}

}