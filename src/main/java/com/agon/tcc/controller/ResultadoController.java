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

import com.agon.tcc.dto.ResultadoDTO;
import com.agon.tcc.model.Resultado;
import com.agon.tcc.service.ResultadoService;

@RestController
@Validated
@RequestMapping("/agon/resultados")
public class ResultadoController {
	
	@Autowired
	private ResultadoService resultadoService;
	
	@GetMapping
	public ResponseEntity<List<ResultadoDTO>> findAll() {
		List<ResultadoDTO> campeonatosDTO = this.resultadoService.findAll();
		return ResponseEntity.ok().body(campeonatosDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResultadoDTO> findById(@PathVariable Long id) {
		ResultadoDTO resultadoDTO = this.resultadoService.findById(id);
		return ResponseEntity.ok().body(resultadoDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody Resultado resultado) {
		this.resultadoService.create(resultado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/resultados/{id}")
				.buildAndExpand(resultado.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody ResultadoDTO resultadoDTO, @PathVariable Long id) {
//		this.resultadoService.update(new ResultadoDTO(id, resultadoDTO.vitorias(), resultadoDTO.empates(), resultadoDTO.derrotas(), 
//													  resultadoDTO.saldoGols(), resultadoDTO.pontos(), resultadoDTO.rodada()));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.resultadoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
