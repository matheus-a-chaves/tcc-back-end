package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.DadosPartidaDTO;
import com.agon.tcc.model.DadosPartida;
import com.agon.tcc.repository.DadosPartidaRepository;

@Service
public class DadosPartidaService {

	@Autowired
	private DadosPartidaRepository dadosPartidaRepository;
		
	public List<DadosPartidaDTO> findAll() {
        return dadosPartidaRepository.findAll()
                .stream()
                .map(dp -> new DadosPartidaDTO(dp.getId(), dp.getPlacar(), dp.getQtdeCartaoVermelho(), dp.getQtdeCartaoAmarelo(), 
                							   dp.getPenaltis(), dp.getEquipe().getId(), dp.getPartida().getId()))
                .collect(Collectors.toList());
    }

    public DadosPartidaDTO findById(Long id) {
        Optional<DadosPartida> dadosPartida = dadosPartidaRepository.findById(id);
        if (dadosPartida.isPresent()) {
            DadosPartida dp = dadosPartida.get();
            return new DadosPartidaDTO(dp.getId(), dp.getPlacar(), dp.getQtdeCartaoVermelho(), dp.getQtdeCartaoAmarelo(), 
					   				   dp.getPenaltis(), dp.getEquipe().getId(), dp.getPartida().getId());
        }
        return null;
    }

	public List<DadosPartidaDTO> findByEquipe(Long id) {
		return dadosPartidaRepository.findByEquipe(id)
				.stream()
				.map(dpe -> new DadosPartidaDTO(dpe.getId(), dpe.getPlacar(), dpe.getQtdeCartaoVermelho(), dpe.getQtdeCartaoAmarelo(), 
												dpe.getPenaltis(), dpe.getEquipe().getId(), dpe.getPartida().getId()))
				.collect(Collectors.toList());
        
	}

	public List<DadosPartidaDTO> findByPartida(Long id) {
		return dadosPartidaRepository.findByPartida(id)
				.stream()
				.map(dpp -> new DadosPartidaDTO(dpp.getId(), dpp.getPlacar(), dpp.getQtdeCartaoVermelho(), dpp.getQtdeCartaoAmarelo(), 
												dpp.getPenaltis(), dpp.getEquipe().getId(), dpp.getPartida().getId()))
				.collect(Collectors.toList());
	}
	
	public List<DadosPartida> findAllByRodadaCampeonato(Integer idRodada) {
		String rodada = String.valueOf(idRodada);
        return dadosPartidaRepository.findAllByRodadaCampeonato(rodada);
    }
	
	public DadosPartidaDTO findByEquipePartida(Long equipeId, Long partidaId) {
        Optional<DadosPartida> dadosPartida = dadosPartidaRepository.findByEquipePartida(equipeId, partidaId);
        if (dadosPartida.isPresent()) {
            DadosPartida dp = dadosPartida.get();
            return new DadosPartidaDTO(dp.getId(), dp.getPlacar(), dp.getQtdeCartaoVermelho(), dp.getQtdeCartaoAmarelo(), 
            						   dp.getPenaltis(), dp.getEquipe().getId(), dp.getPartida().getId());
        }
        return null;
    }

	@Transactional
    public void update(DadosPartidaDTO dadosPartidaDTO, Long equipeId, Long partidaId) {
        Optional<DadosPartida> optPartida = dadosPartidaRepository.findByEquipePartida(equipeId, partidaId);
        if (optPartida.isPresent()) {
            DadosPartida dadosPartida = optPartida.get();
            dadosPartida.setPlacar(dadosPartidaDTO.placar());
            dadosPartida.setQtdeCartaoVermelho(dadosPartidaDTO.qtdeCartaoVermelho());
            dadosPartida.setQtdeCartaoAmarelo(dadosPartidaDTO.qtdeCartaoAmarelo());
            dadosPartida.setPenaltis(dadosPartidaDTO.penaltis());
            
            try {
            	dadosPartidaRepository.save(dadosPartida);
            } catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível atualizar os dados da equipe " + dadosPartida.getEquipe().getNome() + 
											" na partida " + dadosPartida.getPartida().getId());
			}
        }
    }
	
	@Transactional
	public void create(DadosPartidaDTO dadosPartidaDTO) {
		dadosPartidaRepository.save(new DadosPartida(dadosPartidaDTO));
	}
		
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.dadosPartidaRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível excluir pois há entidades relacionadas!");
			}
		}
	}
}
