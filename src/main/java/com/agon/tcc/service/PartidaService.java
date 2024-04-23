package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.PartidaDTO;
import com.agon.tcc.model.Partida;
import com.agon.tcc.repository.CampeonatoRepository;
import com.agon.tcc.repository.PartidaRepository;
import com.agon.tcc.repository.ResultadoRepository;

@Service
public class PartidaService {
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	public PartidaService(PartidaRepository partidaRepository, CampeonatoRepository campeonatoRepository, ResultadoRepository resultadoRepository) {
        this.partidaRepository = partidaRepository;
    }

    public List<PartidaDTO> findAll() {
        return partidaRepository.findAll()
                .stream()
                .map(c -> new PartidaDTO(c.getId(), c.getDataPartida(), c.getEndereco(), c.getEquipes(), c.getPlacarA(), c.getPlacarB(), c.getCampeonato().getId(), c.getResultado().getId()))
                .collect(Collectors.toList());
    }

    public PartidaDTO findById(Long id) {
        Optional<Partida> partida = partidaRepository.findById(id);
        if (partida.isPresent()) {
            Partida p = partida.get();
            return new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEquipes(), p.getPlacarA(), p.getPlacarB(), p.getCampeonato().getId(), p.getResultado().getId());
        }
        return null;
    }

    public List<PartidaDTO> findByCampeonatoId(Long id) {
        return partidaRepository.findByCampeonato(id)
                .stream()
                .map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEquipes(), p.getPlacarA(), p.getPlacarB(), p.getCampeonato().getId(), p.getResultado().getId()))
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
	
//	public List<PartidaDTO> findAll() {
//		return PartidaRepository.findAll()
//				.stream()
//				.map(c -> new PartidaDTO(c.getId(), c.getDataPartida(), c.getEndereco(), c.getEquipes(), c.getPlacarA(), c.getPlacarB()))
//				.collect(Collectors.toList());
//	}
//	
//	public PartidaDTO findById(Long id) {
//		Optional<Partida> partida = PartidaRepository.findById(id);
//		if (partida.isPresent()) {
//			Partida p = partida.get();
//			return new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEquipes(), p.getPlacarA(), p.getPlacarB());
//		}
//		return null;
//	}
//	
//	public List<PartidaDTO> findByCampeonatoId(Long id) {
//		return PartidaRepository.findByCampeonatoId(id)
//				.stream()
//				.map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEquipes(), p.getPlacarA(), p.getPlacarB()))
//				.collect(Collectors.toList());
//	}
	
	@Transactional
	public void create(PartidaDTO partidaDTO) {
		partidaRepository.save(new Partida(partidaDTO));
	}

//	@Transactional
//	public void update(PartidaDTO partidaDTO) {
//		Partida novaPart = new Partida(findById(partidaDTO.id()));
//		novaPart.setDataPartida(partidaDTO.dataPartida());
//		novaPart.setEndereco(partidaDTO.endereco());
//		novaPart.setPlacarA(partidaDTO.placarA());
//		novaPart.setPlacarB(partidaDTO.placarB());
//		this.PartidaRepository.save(novaPart);
//	}
	
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
