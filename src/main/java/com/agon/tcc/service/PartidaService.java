package com.agon.tcc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.AmistosoDTO;
import com.agon.tcc.dto.PartidaDTO;
import com.agon.tcc.model.DadosPartida;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.Partida;
import com.agon.tcc.repository.PartidaRepository;

@Service
public class PartidaService {
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private EquipeService equipeService;
		
    public List<PartidaDTO> findAll() {
        return partidaRepository.findAll()
                .stream()
                .map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEtapaCampeonato(), p.getGrupo(), p.getDadosPartidas(), p.getAmistoso()))
                .collect(Collectors.toList());
    }

    public PartidaDTO findById(Long id) {
        Optional<Partida> partida = partidaRepository.findById(id);
        if (partida.isPresent()) {
            Partida p = partida.get();
            return new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEtapaCampeonato(), p.getGrupo(), p.getDadosPartidas(), p.getAmistoso());
        }
        return null;
    }

//    public List<PartidaDTO> findByCampeonato(Long id) {
//        return partidaRepository.findByCampeonato(id)
//                .stream()
//                .map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEtapaCampeonato(), p.getGrupo(), p.getDadosPartidas()))
//                .collect(Collectors.toList());
//    }

    @Transactional
    public void update(PartidaDTO partidaDTO) {
        Optional<Partida> optPartida = partidaRepository.findById(partidaDTO.id());
        if (optPartida.isPresent()) {
            Partida partida = optPartida.get();
            partida.setDataPartida(partidaDTO.dataPartida());
            partida.setEndereco(partidaDTO.endereco());
            partida.setEtapaCampeonato(partidaDTO.etapaCampeonato());
            partida.setGrupo(partidaDTO.grupo());
            
            try {
            	partidaRepository.save(partida);
            } catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível atualizar a partida " + partida.getId());
			}
        }
    }
	
	@Transactional
	public void create(PartidaDTO partidaDTO) {
		partidaRepository.save(new Partida(partidaDTO));
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
	
	
//	private void gerarPartidaAmistoso(List<Equipe> equipes, PartidaDTO partidaDTO) {
//        Partida partida = new Partida();
//        partida.setDataPartida(partidaDTO.dataPartida());
//        List<DadosPartida> dadosPartidas = new ArrayList<>();
//
//        for (Equipe equipe : equipes) {
//            DadosPartida dados = new DadosPartida();
//            dados.setEquipe(equipe);
//            dados.setPartida(partida);
//            dados.setPlacar(0);
//            dados.setQtdeCartaoVermelho(0);
//            dados.setQtdeCartaoAmarelo(0);
//            dados.setPenaltis(0);
//            dadosPartidas.add(dados);
//        }
//        partida.setDadosPartidas(dadosPartidas);
//
//        // Salvar a partida e os dados de partida em cascata
//        partidaRepository.save(partida);
//    }
	
	/**
	 * Método de geração de Partida e DadosPartida
	 * @param equipeCasa
	 * @param equipeVisitante
	 * @param etapa
	 * @param dataPartida
	 */
	public Partida gerarPartida(AmistosoDTO amistosoDTO) {
        Equipe equipeCasa = new Equipe(equipeService.findById(amistosoDTO.idEquipeCasa()));
        Equipe equipeVisitante = new Equipe(equipeService.findById(amistosoDTO.idEquipeVisitante()));

        Partida partida = new Partida();
        partida.setDataPartida(amistosoDTO.dataHora());
        partida.setEndereco(amistosoDTO.endereco());

        DadosPartida dadosCasa = new DadosPartida();
        dadosCasa.setEquipe(equipeCasa);
        dadosCasa.setPartida(partida);
        dadosCasa.setPlacar(0);
        dadosCasa.setQtdeCartaoVermelho(0);
        dadosCasa.setQtdeCartaoAmarelo(0);
        dadosCasa.setPenaltis(0);

        DadosPartida dadosVisitante = new DadosPartida();
        dadosVisitante.setEquipe(equipeVisitante);
        dadosVisitante.setPartida(partida);
        dadosVisitante.setPlacar(0);
        dadosVisitante.setQtdeCartaoVermelho(0);
        dadosVisitante.setQtdeCartaoAmarelo(0);
        dadosVisitante.setPenaltis(0);

        List<DadosPartida> dadosPartidas = new ArrayList<>();
        dadosPartidas.add(dadosCasa);
        dadosPartidas.add(dadosVisitante);

        partida.setDadosPartidas(dadosPartidas);
        
        return partidaRepository.save(partida);
    }
}
