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

import com.agon.tcc.dto.FormatoDTO;
import com.agon.tcc.service.FormatoService;

@RestController
@Validated
@RequestMapping("/agon/formatos")
public class FormatoController {
	
    @Autowired
    private FormatoService formatoService;

    @GetMapping
    public ResponseEntity<List<FormatoDTO>> findAll() {
        List<FormatoDTO> formatoDTO = this.formatoService.findAll();
        return ResponseEntity.ok().body(formatoDTO);
    }

	@GetMapping("/{id}")
	public ResponseEntity<FormatoDTO> findById(@PathVariable Long id) {
		FormatoDTO formatoDTO = this.formatoService.findById(id);
		return ResponseEntity.ok().body(formatoDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody FormatoDTO formatoDTO) {
		this.formatoService.create(formatoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/formatos/{id}")
				.buildAndExpand(formatoDTO.codigoFormato())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody FormatoDTO formatoDTO, @PathVariable Long id) {
		this.formatoService.update(new FormatoDTO(id, formatoDTO.descricaoFormato()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.formatoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
