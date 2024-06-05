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
import com.agon.tcc.model.Usuario;
import com.agon.tcc.service.EquipeService;
import com.agon.tcc.service.UsuarioService;

@RestController
@Validated
@RequestMapping("/agon/times")
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
	
	@GetMapping("/equipe/{id}/jogadores")
	public ResponseEntity<List<UsuarioDTO>> findJogadoresByEquipe (@PathVariable Long id) {
		List<UsuarioDTO> usuariosDTO = this.usuarioService.findAllJogadoresByEquipe(id);
		return ResponseEntity.ok().body(usuariosDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody EquipeDTO equipeDTO) {
		this.equipeService.create(equipeDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/times/{id}")
				.buildAndExpand(equipeDTO.id())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody EquipeDTO equipeDTO, @PathVariable Long id) {
		this.equipeService.update(new EquipeDTO(id, equipeDTO.nome(), equipeDTO.imagem(), equipeDTO.modalidade(),
//												equipeDTO.equipeGrupos(), equipeDTO.usuarios(), equipeDTO.dadosPartidas()));
												equipeDTO.equipeGrupos(), equipeDTO.dadosPartidas()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.equipeService.delete(id);
		return ResponseEntity.noContent().build();
	}

}