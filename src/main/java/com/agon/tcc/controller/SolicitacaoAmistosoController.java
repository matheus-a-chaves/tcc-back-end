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

import com.agon.tcc.dto.SolicitacaoAmistosoDTO;
import com.agon.tcc.service.SolicitacaoAmistosoService;

@RestController
@Validated
@RequestMapping("/agon/solicitacoes")
public class SolicitacaoAmistosoController {

	@Autowired
	private SolicitacaoAmistosoService solicitacaoAmistosoService;
	
	@GetMapping
	public ResponseEntity<List<SolicitacaoAmistosoDTO>> findAll() {
		List<SolicitacaoAmistosoDTO> amistososDTO = this.solicitacaoAmistosoService.findAll();
		return ResponseEntity.ok().body(amistososDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SolicitacaoAmistosoDTO> findById(@PathVariable Long id) {
		SolicitacaoAmistosoDTO solicitacaoAmistosoDTO = this.solicitacaoAmistosoService.findById(id);
		return ResponseEntity.ok().body(solicitacaoAmistosoDTO);
	}
	
	@GetMapping("/idAtletica/{idAtletica}")
	public ResponseEntity<List<SolicitacaoAmistosoDTO>> buscarSolicitacoesComEndereco(@PathVariable Long idAtletica) {
		List<SolicitacaoAmistosoDTO> solicitacoesDTO = this.solicitacaoAmistosoService.buscarSolicitacaoComEndereco(idAtletica);
		return ResponseEntity.ok().body(solicitacoesDTO);
	}
		
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody SolicitacaoAmistosoDTO solicitacaoAmistosoDTO) {
		this.solicitacaoAmistosoService.create(solicitacaoAmistosoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/amistosos/{id}")
				.buildAndExpand(solicitacaoAmistosoDTO.id())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody SolicitacaoAmistosoDTO solicitacaoAmistosoDTO, @PathVariable Long id) {
		this.solicitacaoAmistosoService.update(new SolicitacaoAmistosoDTO(solicitacaoAmistosoDTO.id(), solicitacaoAmistosoDTO.dataSolicitacao(), solicitacaoAmistosoDTO.amistoso(), 
																		  solicitacaoAmistosoDTO.equipeVisitante(), solicitacaoAmistosoDTO.status(), null));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.solicitacaoAmistosoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}/responder/{resposta}")
    public ResponseEntity<Void> responderSolicitacao(@PathVariable Long id, @PathVariable String resposta) {
        solicitacaoAmistosoService.responderSolicitacao(id, resposta);
        return ResponseEntity.ok().build();
    }
	
}
