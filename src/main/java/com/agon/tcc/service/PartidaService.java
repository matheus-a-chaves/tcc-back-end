package com.agon.tcc.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.AgendaDTO;
import com.agon.tcc.dto.DadosPartidaDTO;
import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.dto.PartidaDTO;
import com.agon.tcc.model.Amistoso;
import com.agon.tcc.model.Campeonato;
import com.agon.tcc.model.DadosPartida;
import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.EtapaCampeonato;
import com.agon.tcc.model.Partida;
import com.agon.tcc.model.Resultado;
import com.agon.tcc.repository.PartidaRepository;

@Service
public class PartidaService {
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private EquipeService equipeService;

    @Autowired
    private ResultadoService resultadoService;

	@Autowired
	private DadosPartidaService dadosPartidaService;
		
    public List<PartidaDTO> findAll() {
        return partidaRepository.findAll()
                .stream()
                .map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEtapaCampeonato(), p.getGrupo(), p.getDadosPartidas(), p.getAmistoso(), p.getCampeonato()))
                .collect(Collectors.toList());
    }

    public PartidaDTO findById(Long id) {
        Optional<Partida> partida = partidaRepository.findById(id);
        if (partida.isPresent()) {
            Partida p = partida.get();
            return new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEtapaCampeonato(), p.getGrupo(), p.getDadosPartidas(), p.getAmistoso(), p.getCampeonato());
        }
        return null;
    }
    
    public List<AgendaDTO> encontrarPartidasPorData(String dataString, Long idEquipe) {
    	List<AgendaDTO> listAgendaDTO = new ArrayList<>();
    	LocalDate data = LocalDate.parse(dataString);
    	
    	List<PartidaDTO> list = partidaRepository.encontrarPartidasPorData(data, idEquipe)
                .stream()
                .map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEtapaCampeonato(), p.getGrupo(), p.getDadosPartidas(), p.getAmistoso(), p.getCampeonato()))
                .collect(Collectors.toList());
    	
    	for(PartidaDTO aux : list) {
    		DadosPartidaDTO dadosPartidaDTO = this.dadosPartidaService.findById(aux.dadosPartidas().get(0).getId());
    		DadosPartidaDTO dadosPartidaTimeDoisDTO = this.dadosPartidaService.findById(aux.dadosPartidas().get(1).getId());
    		EquipeDTO equipeUmDTO = this.equipeService.findById(dadosPartidaDTO.equipeId());
    		EquipeDTO equipeDoisDTO = this.equipeService.findById(dadosPartidaTimeDoisDTO.equipeId());
    		Equipe equipeUm = new Equipe(equipeUmDTO);
    		Equipe equipeDois = new Equipe(equipeDoisDTO);
    		
    		AgendaDTO agendaDTO = new AgendaDTO(aux.amistoso().getId(),aux.dataPartida(), aux.endereco(), equipeUm, equipeDois);
    		listAgendaDTO.add(agendaDTO);
    	}
    	
    	return listAgendaDTO;
    }
    
    public List<AgendaDTO> findAmistososByEquipe(Long idEquipe) {
    	List<AgendaDTO> listAgendaDTO = new ArrayList<>();
    	
    	List<PartidaDTO> list = partidaRepository.findAmistososByEquipe(idEquipe)
                .stream()
                .map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEtapaCampeonato(), p.getGrupo(), p.getDadosPartidas(), p.getAmistoso(), p.getCampeonato()))
                .collect(Collectors.toList());
    	
    	for(PartidaDTO aux : list) {
    		DadosPartidaDTO dadosPartidaDTO = this.dadosPartidaService.findById(aux.dadosPartidas().get(0).getId());
    		DadosPartidaDTO dadosPartidaTimeDoisDTO = this.dadosPartidaService.findById(aux.dadosPartidas().get(1).getId());
    		EquipeDTO equipeUmDTO = this.equipeService.findById(dadosPartidaDTO.equipeId());
    		EquipeDTO equipeDoisDTO = this.equipeService.findById(dadosPartidaTimeDoisDTO.equipeId());
    		Equipe equipeUm = new Equipe(equipeUmDTO);
    		Equipe equipeDois = new Equipe(equipeDoisDTO);
    		
    		AgendaDTO agendaDTO = new AgendaDTO(aux.amistoso().getId(),aux.dataPartida(), aux.endereco(), equipeUm, equipeDois);
    		listAgendaDTO.add(agendaDTO);
    	}
    	
    	return listAgendaDTO;
    }

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
	
	@Transactional
	public void create(Partida partida) {
		partidaRepository.save(partida);
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
	
	/**
	 * Método de geração de Partida e DadosPartida
	 * @param equipeCasa
	 * @param equipeVisitante
	 * @param etapa
	 * @param dataPartida
	 */
	public void gerarPartidaAmistoso(Long idEquipeCasa, Long idEquipeVisitante, Endereco enderecoAmistoso, Amistoso amistoso) {
        Equipe equipeCasa = new Equipe(equipeService.findById(idEquipeCasa));
        Equipe equipeVisitante = new Equipe(equipeService.findById(idEquipeVisitante));
        
        Partida partida = new Partida();
        partida.setDataPartida(amistoso.getDataHorario());
        partida.setEndereco(enderecoAmistoso);
        partida.setAmistoso(amistoso);

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
        
        partidaRepository.save(partida);
    }
	
	/**
	 * Método de geração de Partidas e DadosPartidas por Etapa de um Campeonato
	 * @param campeonato
	 * @param etapa
	 * @param equipesCamp
	 * @param enderecoDefault
	 */
    public void gerarPartidasPontosCorridos(Campeonato campeonato, EtapaCampeonato etapa, List<Equipe> equipesCamp, Endereco enderecoDefault) {
//		if (etapa.getNomeEtapa().equals("Pontos Corridos")) {
//			// Gerando as partidas por PONTOS CORRIDOS
//			for (int i = 0; i < equipesCamp.size(); i++) {
//	            for (int j = i + 1; j < equipesCamp.size(); j++) {
//	                Partida partida = new Partida();
//	                partida.setDataPartida(LocalDateTime.now().plusDays(3));
//	                partida.setEtapaCampeonato(etapa);
//	                partida.setEndereco(enderecoDefault);
//	                
//	                DadosPartida dadosPartida1 = new DadosPartida();
//	                dadosPartida1.setEquipe(equipesCamp.get(i));
//	                dadosPartida1.setPartida(partida);
//	
//	                DadosPartida dadosPartida2 = new DadosPartida();
//	                dadosPartida2.setEquipe(equipesCamp.get(j));
//	                dadosPartida2.setPartida(partida);
//	
//	                partida.setDadosPartidas(Arrays.asList(dadosPartida1, dadosPartida2));
//	                this.create(partida);
//	                	                
//	                Resultado resultadoEquipe1 = new Resultado();
//	                resultadoEquipe1.setEtapaCampeonato(etapa);
//	                resultadoEquipe1.setDadosPartida(dadosPartida1);
//	                resultadoEquipe1.setRodada("1");//inicia na rodada 1
//	                resultadoService.create(resultadoEquipe1);
//	                
//	                Resultado resultadoEquipe2 = new Resultado();
//	                resultadoEquipe2.setEtapaCampeonato(etapa);
//	                resultadoEquipe2.setDadosPartida(dadosPartida2);
//	                resultadoEquipe2.setRodada("1");//inicia na rodada 1
//	                resultadoService.create(resultadoEquipe2);
//	            }
//	        }
//		}
        int totalEquipes = equipesCamp.size();
        int totalPartidas = totalEquipes * (totalEquipes - 1);
        int partidasPorRodada = totalPartidas / totalEquipes;
        int rodadaAtual = 1;

        List<Partida> partidas = new ArrayList<>();

        // Gerando todas as partidas de ida e volta
        for (int i = 0; i < totalEquipes; i++) {
            for (int j = 0; j < totalEquipes; j++) {
                if (i != j) {
                    Partida partida = new Partida();
                    partida.setDataPartida(LocalDateTime.now().plusDays(3 * rodadaAtual));
                    partida.setEtapaCampeonato(etapa);
                    partida.setEndereco(enderecoDefault);

                    DadosPartida dadosPartida1 = new DadosPartida();
                    dadosPartida1.setEquipe(equipesCamp.get(i));
                    dadosPartida1.setPartida(partida);

                    DadosPartida dadosPartida2 = new DadosPartida();
                    dadosPartida2.setEquipe(equipesCamp.get(j));
                    dadosPartida2.setPartida(partida);

                    partida.setDadosPartidas(Arrays.asList(dadosPartida1, dadosPartida2));
                    partidas.add(partida);

                    Resultado resultadoEquipe1 = new Resultado();
                    resultadoEquipe1.setEtapaCampeonato(etapa);
                    resultadoEquipe1.setDadosPartida(dadosPartida1);
                    resultadoEquipe1.setRodada(rodadaAtual);
                    resultadoService.create(resultadoEquipe1);

                    Resultado resultadoEquipe2 = new Resultado();
                    resultadoEquipe2.setEtapaCampeonato(etapa);
                    resultadoEquipe2.setDadosPartida(dadosPartida2);
                    resultadoEquipe2.setRodada(rodadaAtual);
                    resultadoService.create(resultadoEquipe2);

                    if (partidas.size() % partidasPorRodada == 0) {
                        rodadaAtual++;
                    }
                }
            }
        }

        // Salvando todas as partidas após a distribuição das rodadas
        for (Partida partida : partidas) {
            partidaRepository.save(partida);
        }
    }
    
	
}
