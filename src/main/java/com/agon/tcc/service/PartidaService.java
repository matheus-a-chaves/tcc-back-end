package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.PartidaDTO;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.Partida;
import com.agon.tcc.repository.EquipeRepository;
import com.agon.tcc.repository.PartidaRepository;

@Service
public class PartidaService {
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;
	
    public List<PartidaDTO> findAll() {
        return partidaRepository.findAll()
                .stream()
                .map(c -> new PartidaDTO(c.getId(), c.getDataPartida(), c.getEndereco(), c.getEquipes(), c.getPlacarA(), 
                						 c.getPlacarB(), c.getCampeonato().getId(), c.getResultado().getId()))
                .collect(Collectors.toList());
    }

    public PartidaDTO findById(Long id) {
        Optional<Partida> partida = partidaRepository.findById(id);
        if (partida.isPresent()) {
            Partida p = partida.get();
            return new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEquipes(), p.getPlacarA(),
            					  p.getPlacarB(), p.getCampeonato().getId(), p.getResultado().getId());
        }
        return null;
    }

    public List<PartidaDTO> findByCampeonato(Long id) {
        return partidaRepository.findByCampeonato(id)
                .stream()
                .map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEquipes(), p.getPlacarA(), 
                						 p.getPlacarB(), p.getCampeonato().getId(), p.getResultado().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(PartidaDTO partidaDTO) {
        Optional<Partida> optPartida = partidaRepository.findById(partidaDTO.id());
        if (optPartida.isPresent()) {
            Partida partida = optPartida.get();
            partida.setDataPartida(partidaDTO.dataPartida());
            partida.setEndereco(partidaDTO.endereco());
            partida.setPlacarA(partidaDTO.placarA());
            partida.setPlacarB(partidaDTO.placarB());
            
            try {
            	partidaRepository.save(partida);
            } catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível atualizar a partida " + partida.getId());
			}
        }
    }
	
	@Transactional
	public void create(PartidaDTO partidaDTO, List<Long> idsEquipes) {
	    Partida partida = new Partida();
	    // Recuperar as equipes do banco de dados com base nos IDs fornecidos
	    List<Equipe> equipes = equipeRepository.findAllById(idsEquipes);
	    partida.setEquipes(equipes);
	    
	    try {
		    partidaRepository.save(partida);
        } catch (Exception e) {
			throw new RuntimeErrorException(null, "Não foi possível salvar a partida " + partida.getId());
		}
	}
		
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.partidaRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível excluir pois há entidades relacionadas!");
			}
		}
	}
}
