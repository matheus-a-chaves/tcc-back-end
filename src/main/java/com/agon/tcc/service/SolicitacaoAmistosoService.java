package com.agon.tcc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.SolicitacaoAmistosoDTO;
import com.agon.tcc.model.Amistoso;
import com.agon.tcc.model.Partida;
import com.agon.tcc.model.SolicitacaoAmistoso;
import com.agon.tcc.model.enums.StatusSolicitacao;
import com.agon.tcc.repository.AmistosoRepository;
import com.agon.tcc.repository.PartidaRepository;
import com.agon.tcc.repository.SolicitacaoAmistosoRepository;

@Service
public class SolicitacaoAmistosoService {

	@Autowired
	private SolicitacaoAmistosoRepository solicitacaoAmistosoRepository;
		
	@Autowired
	private AmistosoRepository amistosoRepository;
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	public List<SolicitacaoAmistosoDTO> findAll() {
		return solicitacaoAmistosoRepository.findAll()
				.stream()
				.map(a -> new SolicitacaoAmistosoDTO(a.getId(), a.getDataSolicitacao(), a.getEquipeVisitante(), a.getEquipeCasa(), null, null,  a.getAmistoso()))
				.collect(Collectors.toList());
	}
	
	public SolicitacaoAmistosoDTO findById(Long id) {
		Optional<SolicitacaoAmistoso> solicitacaoAmistoso = solicitacaoAmistosoRepository.findById(id);
		if (solicitacaoAmistoso.isPresent()) {
			SolicitacaoAmistoso a = solicitacaoAmistoso.get();
			return new SolicitacaoAmistosoDTO(a.getId(), a.getDataSolicitacao(), a.getEquipeVisitante(), a.getEquipeCasa(),null, null,  a.getAmistoso());
		}
		return null;
	}
	
	public List<SolicitacaoAmistosoDTO> buscarSolicitacaoComEndereco(Long atleticaId) {
		List<Partida> partidas = partidaRepository.buscarPartidasAtletica(atleticaId);
		List<SolicitacaoAmistoso> solicitacoes = solicitacaoAmistosoRepository.buscarSolicitacoesAtletica(atleticaId);
		List<SolicitacaoAmistosoDTO> solicitacaoAmistosoDTOs = new ArrayList<>();

	    // Assume que as listas têm o mesmo tamanho e estão relacionadas de alguma forma.
	    // Se não for o caso, essa abordagem precisará ser ajustada.
	    int maxSize = Math.max(partidas.size(), solicitacoes.size());

	    for (int i = 0; i < maxSize; i++) {
	        Partida partida = i < partidas.size() ? partidas.get(i) : null;
	        SolicitacaoAmistoso solicitacao = i < solicitacoes.size() ? solicitacoes.get(i) : null;

	        if (solicitacao != null && partida != null) {
	            SolicitacaoAmistosoDTO solicitacaoDTO = new SolicitacaoAmistosoDTO(solicitacao.getId(),
						solicitacao.getDataSolicitacao(), solicitacao.getEquipeVisitante(), solicitacao.getEquipeCasa(),solicitacao.getStatus(), partida.getEndereco(),  partida.getAmistoso());
	            solicitacaoAmistosoDTOs.add(solicitacaoDTO);
	        }
	    }

	    return solicitacaoAmistosoDTOs;
	}
	
	@Transactional
	public void create(SolicitacaoAmistosoDTO solicitacaoAmistosoDTO) {
		solicitacaoAmistosoRepository.save(new SolicitacaoAmistoso(solicitacaoAmistosoDTO));
	}
	
	@Transactional
	public void create(SolicitacaoAmistoso solicitacaoAmistoso) {
		solicitacaoAmistosoRepository.save(solicitacaoAmistoso);
	}
	
	@Transactional
	public void update(SolicitacaoAmistosoDTO solicitacaoAmistosoDTO) {
		SolicitacaoAmistoso solicitacaoAmistoso = new SolicitacaoAmistoso(findById(solicitacaoAmistosoDTO.id()));
		solicitacaoAmistoso.setStatus(solicitacaoAmistoso.getStatus());
		this.solicitacaoAmistosoRepository.save(solicitacaoAmistoso);
	}
	
	@Transactional
	public void update(SolicitacaoAmistoso solicitacaoAmistoso) {
		this.solicitacaoAmistosoRepository.save(solicitacaoAmistoso);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.solicitacaoAmistosoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}

    public void responderSolicitacao(Long idSolicitacao, String resposta) {
    	SolicitacaoAmistoso solicitacao = new SolicitacaoAmistoso(this.findById(idSolicitacao));
		
        if ("aceitar".equalsIgnoreCase(resposta)) {
        	solicitacao.setStatus(StatusSolicitacao.CONFIRMADO);
        } else if ("recusar".equalsIgnoreCase(resposta)) {
        	solicitacao.setStatus(StatusSolicitacao.RECUSADO);
        } else {
        	solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        }
        this.update(solicitacao);

        Amistoso amistoso = solicitacao.getAmistoso();
        amistoso.setStatusAmistoso(solicitacao.getStatus());
        amistosoRepository.save(amistoso);
    }
}
