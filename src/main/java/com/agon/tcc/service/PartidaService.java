package com.agon.tcc.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import com.agon.tcc.dto.*;
import com.agon.tcc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.repository.EquipeRepository;
import com.agon.tcc.repository.PartidaRepository;

@Service
public class PartidaService {
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private ResultadoService resultadoService;
	
	@Autowired
	private EquipeService equipeService;

	@Autowired
	private DadosPartidaService dadosPartidaService;
	
	@Autowired
	private EtapaCampeonatoService etapaCampeonatoService;
		
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
    	
    	for (PartidaDTO aux : list) {
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
    	
    	for (PartidaDTO aux : list) {
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
    
    public List<PartidaDTO> findByCampeonato(Long idCampeonato) {
    	return partidaRepository.findByCampeonato(idCampeonato)
                .stream()
                .map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEtapaCampeonato(), p.getGrupo(), p.getDadosPartidas(), p.getAmistoso(), p.getCampeonato()))
                .collect(Collectors.toList());
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
            partida.setAmistoso(partidaDTO.amistoso());
            partida.setCampeonato(partidaDTO.campeonato());
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
	@Transactional
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
	
	@Transactional
	public void gerarPartidaCampeonato(Campeonato campeonato, Equipe equipe1, Equipe equipe2, Endereco endereco, EtapaCampeonato etapa, Integer rodada) {
	    Partida partida = new Partida();
	    partida.setEtapaCampeonato(etapa);
	    partida.setEndereco(endereco);
	    partida.setCampeonato(campeonato);

	    DadosPartida dadosPartida1 = new DadosPartida();
	    dadosPartida1.setEquipe(equipe1);
	    dadosPartida1.setPartida(partida);
	    dadosPartida1.setPlacar(0);
	    dadosPartida1.setQtdeCartaoVermelho(0);
	    dadosPartida1.setQtdeCartaoAmarelo(0);
	    dadosPartida1.setPenaltis(0);
	    dadosPartida1.setDadosAtualizados(false);

	    DadosPartida dadosPartida2 = new DadosPartida();
	    dadosPartida2.setEquipe(equipe2);
	    dadosPartida2.setPartida(partida);
	    dadosPartida2.setPlacar(0);
	    dadosPartida2.setQtdeCartaoVermelho(0);
	    dadosPartida2.setQtdeCartaoAmarelo(0);
	    dadosPartida2.setPenaltis(0);
	    dadosPartida2.setDadosAtualizados(false);

	    partida.setDadosPartidas(Arrays.asList(dadosPartida1, dadosPartida2));
	    this.create(partida);

	    Resultado resultadoEquipe1 = new Resultado();
	    resultadoEquipe1.setEtapaCampeonato(etapa);
	    resultadoEquipe1.setDadosPartida(dadosPartida1);
	    resultadoEquipe1.setRodada(rodada);
	    resultadoEquipe1.setVitorias(0);
	    resultadoEquipe1.setEmpates(0);
	    resultadoEquipe1.setDerrotas(0);
	    resultadoEquipe1.setSaldoGols("0");
	    resultadoEquipe1.setPontos(0);
	    resultadoService.create(resultadoEquipe1);

	    Resultado resultadoEquipe2 = new Resultado();
	    resultadoEquipe2.setEtapaCampeonato(etapa);
	    resultadoEquipe2.setDadosPartida(dadosPartida2);
	    resultadoEquipe2.setRodada(rodada);
	    resultadoEquipe2.setVitorias(0);
	    resultadoEquipe2.setEmpates(0);
	    resultadoEquipe2.setDerrotas(0);
	    resultadoEquipe2.setSaldoGols("0");
	    resultadoEquipe2.setPontos(0);
	    resultadoService.create(resultadoEquipe2);
	}
	
    @Transactional
    public void gerarPartidasEliminatoriaSimples(Endereco endereco, Campeonato campeonato, Integer idRodada, EtapaCampeonato etapaCampeonato) throws Exception {
    	try {
    		
        	if(idRodada == 1) {
        		
        		if(endereco == null) {
            		throw new RuntimeErrorException(null, "Favor cadastrar um endereço para o campeonato!");
            	}
        		
        		this.gerarPrimeiraRodada(campeonato, etapaCampeonato, endereco);
        	} else {
        		this.gerarProximaRodada(campeonato, idRodada);
        	}
        	
    	} catch(Exception ex) {
    		throw new RuntimeErrorException(null, ex.getMessage());
    	}
    }
    
    @Transactional
    public void gerarProximaRodada(Campeonato campeonato, Integer idRodada) {
    	
    	List<EtapaCampeonatoDTO> etapasCampeonato = this.etapaCampeonatoService.findByCampeonato(campeonato.getId());
    	
    	Integer rodadaAtual = idRodada;
    	EtapaCampeonato etapaCampeonato = new EtapaCampeonato(etapasCampeonato.get(0));
    	
    	if(idRodada > etapaCampeonato.getTotalRodadas()) {
    		throw new RuntimeErrorException(null, "O campeonato não possui mais rodadas!");
    	}
    	
    	List<DadosPartida> dadosPartida = this.dadosPartidaService.findAllByRodadaCampeonato(idRodada - 1, etapaCampeonato.getId());
    	int contDadosAtualizados = 0;
    	
    	for(DadosPartida dp : dadosPartida) {
    		if(dp.isDadosAtualizados()) {
    			contDadosAtualizados++;
    		}
    	}
    	
    	Endereco endereco = new Endereco();
    	endereco = dadosPartida.get(0).getPartida().getEndereco();
    	
    	if(contDadosAtualizados != dadosPartida.size()) {
    		throw new RuntimeErrorException(null, "Favor preencher todos os resultados das partidas da rodada " + (idRodada-1));
    	} else {
    		List<Equipe> equipeVencedorasRodadaAnterior =  this.equipeService.findEquipesVencedorasRodadaAnterior(idRodada-1);
    		
    		int qtdadePartidas = equipeVencedorasRodadaAnterior.size()/2;
    		List<Equipe> grupoUm = equipeVencedorasRodadaAnterior.subList(0, qtdadePartidas);
        	List<Equipe> grupoDois = equipeVencedorasRodadaAnterior.subList(qtdadePartidas, equipeVencedorasRodadaAnterior.size());
        	
        	for(int i = 0; i < qtdadePartidas; i++) {
        		
        		Partida partida = new Partida();
                partida.setDataPartida(LocalDateTime.now().plusDays(3));
                partida.setEtapaCampeonato(etapaCampeonato);
                partida.setEndereco(endereco);
                partida.setCampeonato(campeonato);
                
                DadosPartida dadosPartida1 = new DadosPartida();
                dadosPartida1.setEquipe(grupoUm.get(i));
                dadosPartida1.setPartida(partida);
                dadosPartida1.setPlacar(0);
                dadosPartida1.setQtdeCartaoAmarelo(0);
                dadosPartida1.setQtdeCartaoVermelho(0);
                dadosPartida1.setPenaltis(0);
                dadosPartida1.setDadosAtualizados(false);
                
                DadosPartida dadosPartida2 = new DadosPartida();
                dadosPartida2.setEquipe(grupoDois.get(i));
                dadosPartida2.setPartida(partida);
                dadosPartida2.setPlacar(0);
                dadosPartida2.setQtdeCartaoAmarelo(0);
                dadosPartida2.setQtdeCartaoVermelho(0);
                dadosPartida2.setPenaltis(0);
                dadosPartida2.setDadosAtualizados(false);
                
        		partida.setDadosPartidas(Arrays.asList(dadosPartida1, dadosPartida2));
        		this.create(partida);
        		
        		Resultado resultadoEquipe1 = new Resultado();
        		resultadoEquipe1.setDadosPartida(dadosPartida1);
        		resultadoEquipe1.setRodada(rodadaAtual);
        		resultadoEquipe1.setVitorias(0);
        		resultadoEquipe1.setEmpates(0);
        		resultadoEquipe1.setDerrotas(0);
        		resultadoEquipe1.setSaldoGols("0");
        		resultadoEquipe1.setPontos(0);
        		resultadoEquipe1.setEtapaCampeonato(etapaCampeonato);
        		resultadoService.create(resultadoEquipe1);
        		
        		Resultado resultadoEquipe2 = new Resultado();
        		resultadoEquipe2.setDadosPartida(dadosPartida2);
        		resultadoEquipe2.setRodada(rodadaAtual);
        		resultadoEquipe2.setVitorias(0);
        		resultadoEquipe2.setEmpates(0);
        		resultadoEquipe2.setDerrotas(0);
        		resultadoEquipe2.setSaldoGols("0");
        		resultadoEquipe2.setPontos(0);
        		resultadoEquipe2.setEtapaCampeonato(etapaCampeonato);
        		resultadoService.create(resultadoEquipe2);
        	}
    	}
    }
    
    @Transactional
    public void gerarPrimeiraRodada(Campeonato campeonato, EtapaCampeonato etapaCampeonato, Endereco endereco) {
    	int qtdadePartidasIniciais = campeonato.getQuantidadeEquipes()/2;
    	
    	List<Equipe> equipesParticipantes = this.equipeRepository.findAllTimesByIdCampeonato(campeonato.getId());
    	List<Equipe> grupoUm = equipesParticipantes.subList(0, qtdadePartidasIniciais);
    	List<Equipe> grupoDois = equipesParticipantes.subList(qtdadePartidasIniciais, equipesParticipantes.size());
    	
    	for(int i = 0; i < qtdadePartidasIniciais; i++) {
    		
    		Partida partida = new Partida();
            partida.setEtapaCampeonato(etapaCampeonato);
            partida.setEndereco(endereco);
            partida.setCampeonato(campeonato);
            
            DadosPartida dadosPartida1 = new DadosPartida();
            dadosPartida1.setEquipe(grupoUm.get(i));
            dadosPartida1.setPartida(partida);
            dadosPartida1.setPlacar(0);
            dadosPartida1.setQtdeCartaoAmarelo(0);
            dadosPartida1.setQtdeCartaoVermelho(0);
            dadosPartida1.setPenaltis(0);
            dadosPartida1.setDadosAtualizados(false);
            
            DadosPartida dadosPartida2 = new DadosPartida();
            dadosPartida2.setEquipe(grupoDois.get(i));
            dadosPartida2.setPartida(partida);
            dadosPartida2.setPlacar(0);
            dadosPartida2.setQtdeCartaoAmarelo(0);
            dadosPartida2.setQtdeCartaoVermelho(0);
            dadosPartida2.setPenaltis(0);
            dadosPartida2.setDadosAtualizados(false);
            
    		partida.setDadosPartidas(Arrays.asList(dadosPartida1, dadosPartida2));
    		this.create(partida);
    		
    		Resultado resultadoEquipe1 = new Resultado();
    		resultadoEquipe1.setDadosPartida(dadosPartida1);
    		resultadoEquipe1.setRodada(1);
    		resultadoEquipe1.setVitorias(0);
    		resultadoEquipe1.setEmpates(0);
    		resultadoEquipe1.setDerrotas(0);
    		resultadoEquipe1.setSaldoGols("0");
    		resultadoEquipe1.setPontos(0);
    		resultadoEquipe1.setEtapaCampeonato(etapaCampeonato);
    		resultadoService.create(resultadoEquipe1);
    		
    		Resultado resultadoEquipe2 = new Resultado();
    		resultadoEquipe2.setDadosPartida(dadosPartida2);
    		resultadoEquipe2.setRodada(1);
    		resultadoEquipe2.setVitorias(0);
    		resultadoEquipe2.setEmpates(0);
    		resultadoEquipe2.setDerrotas(0);
    		resultadoEquipe2.setSaldoGols("0");
    		resultadoEquipe2.setPontos(0);
    		resultadoEquipe2.setEtapaCampeonato(etapaCampeonato);
    		resultadoService.create(resultadoEquipe2);
    	}
    }


	public List<PartidasResponseDTO> findPartidasByIdAtltica(Long idCampeonato) {
		List<PartidaDTO> partidas = this.findByCampeonato(idCampeonato);

		List<PartidasResponseDTO> partidasResponse = new ArrayList<>();

		for (PartidaDTO p : partidas) {
			PartidasResponseDTO partidaResponse = new PartidasResponseDTO();
			partidaResponse.setIdPartida(p.id());
			partidaResponse.setNomeEtapa(p.etapaCampeonato().getNomeEtapa());
			partidaResponse.setCampeonato(p.campeonato().getId());
			partidaResponse.setTotalRodadas(p.etapaCampeonato().getTotalRodadas().longValue());
			partidaResponse.setEquipeUm(p.dadosPartidas().get(0).getEquipe());
			partidaResponse.setEquipeDois(p.dadosPartidas().get(1).getEquipe());
			partidaResponse.setEndereco(p.endereco());
			partidaResponse.setDataPartida(p.dataPartida());

			Integer idRodada = this.resultadoService.findByDadosPartida(p.dadosPartidas().get(0).getId());

			partidaResponse.setIdRodada(idRodada);
			if(p.dataPartida() != null)
			{
				if (p.dataPartida().isBefore(LocalDateTime.now())) {
					partidaResponse.setPartidaExpirada(true);
				}
			}


			boolean partidaFinalizada = false;
			for (DadosPartida dadosPartida : p.dadosPartidas()) {
				if (dadosPartida.isDadosAtualizados()) {
					partidaFinalizada = true;
					break;
				}
			}
			partidaResponse.setPartidaFinalizada(partidaFinalizada);

			partidasResponse.add(partidaResponse);
		}

		return partidasResponse;
	}




}
