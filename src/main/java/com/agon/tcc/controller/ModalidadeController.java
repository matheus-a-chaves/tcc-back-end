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

import com.agon.tcc.dto.ModalidadeDTO;
import com.agon.tcc.service.ModalidadeService;

@RestController
@Validated
@RequestMapping("/agon/modalidades")
public class ModalidadeController {
	
    @Autowired
    private ModalidadeService modalidadeService;

    @GetMapping
    public ResponseEntity<List<ModalidadeDTO>> findAll() {
        List<ModalidadeDTO> modalidadeDTO = this.modalidadeService.findAll();
        return ResponseEntity.ok().body(modalidadeDTO);
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<ModalidadeDTO> findById(@PathVariable Long id) {
		ModalidadeDTO modalidadeDTO = this.modalidadeService.findById(id);
		return ResponseEntity.ok().body(modalidadeDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody ModalidadeDTO modalidadeDTO) {
		this.modalidadeService.create(modalidadeDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/formatos/{id}")
				.buildAndExpand(modalidadeDTO.codigoModalidade())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody ModalidadeDTO modalidadeDTO, @PathVariable Long id) {
		this.modalidadeService.update(new ModalidadeDTO(id, modalidadeDTO.descricaoModalidade()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.modalidadeService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
