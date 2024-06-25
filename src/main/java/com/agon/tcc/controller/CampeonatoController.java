package com.agon.tcc.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import com.agon.tcc.model.Endereco;
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
import com.agon.tcc.model.PartidaChaveamento;
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
	
	@PostMapping("/create/usuario/{idUsuario}")
	public ResponseEntity<Void> create(@RequestBody CampeonatoDTO campeonatoDTO, @PathVariable Long idUsuario) {
		this.campeonatoService.create(campeonatoDTO, idUsuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/agon/campeonatos/{id}")
				.buildAndExpand(campeonatoDTO.id())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("/{idCampeonato}/modalidade/{idModalidade}/adicionar")
	public ResponseEntity<?> adicionarEquipe(@RequestBody Map<String, String> cnpjValue, @PathVariable Long idCampeonato, @PathVariable Long idModalidade) {
		String cnpjAtletica = cnpjValue.get("cnpj");
		try {
			this.campeonatoService.adicionarEquipe(cnpjAtletica, idCampeonato, idModalidade);
			return ResponseEntity.ok().build();
		} catch(Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@GetMapping("/{idCampeonato}/equipe/{idEquipe}/remover")
	public ResponseEntity<Void> removerEquipe(@PathVariable Long idCampeonato, @PathVariable Long idEquipe) {
		try {
			this.campeonatoService.removerEquipe(idCampeonato, idEquipe);
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
	@PostMapping("/{id}/iniciar/{rodada}")
    public ResponseEntity<?> iniciarCampeonato(@PathVariable Long id, @PathVariable Integer rodada, @RequestBody Endereco endereco) throws Exception {
		try {
	        campeonatoService.iniciarCampeonato(id, endereco, rodada);
	        return ResponseEntity.ok().build();
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
    }
	
	@PostMapping("/{idCampeonato}/chaveamento")
    public ResponseEntity<?> visualizarChaveamento(@PathVariable Long idCampeonato) throws Exception {
		try {
			Map<Integer, List<PartidaChaveamento>> partidasChaveamento = campeonatoService.visualizarChaveamento(idCampeonato);
	        return ResponseEntity.ok().body(partidasChaveamento);
		}catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
        
    }

	@GetMapping("/interno/atletica/{idEquipe}/modalidade/{idModalidade}")
	public ResponseEntity<List<CampeonatoDTO>> findAllIntByModalidadeId(@PathVariable Long idEquipe, @PathVariable Long idModalidade) {
		List<CampeonatoDTO> campeonatosDTO = this.campeonatoService.findAllIntByModalidadeId(idEquipe, idModalidade);
		return ResponseEntity.ok().body(campeonatosDTO);
	}

	@GetMapping("externo/atletica/{idAtletica}/modalidade/{idModalidade}")
	public ResponseEntity<List<CampeonatoDTO>> findAllExtByModalidadeId(@PathVariable Long idAtletica, @PathVariable Long idModalidade) {
		List<CampeonatoDTO> campeonatosDTO = this.campeonatoService.findAllExtByModalidadeId(idAtletica, idModalidade);
		return ResponseEntity.ok().body(campeonatosDTO);
	}

	@PostMapping("gerar/{idCampeonato}/grupos")
	public ResponseEntity<List<CampeonatoDTO>> gerarFaseDeGrupos(@RequestBody Endereco endereco, @PathVariable Long idCampeonato) {
		List<CampeonatoDTO> campeonatosDTO = this.campeonatoService.gerarFaseDeGrupos(endereco, idCampeonato);
		return ResponseEntity.ok().body(campeonatosDTO);
	}



}
