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
import com.agon.tcc.dto.CampeonatoDTO;
import com.agon.tcc.dto.DadosPartidaDTO;
import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.dto.EtapaCampeonatoDTO;
import com.agon.tcc.dto.PartidaDTO;
import com.agon.tcc.model.Amistoso;
import com.agon.tcc.model.Campeonato;
import com.agon.tcc.model.DadosPartida;
import com.agon.tcc.model.Endereco;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.EquipeGrupo;
import com.agon.tcc.model.EtapaCampeonato;
import com.agon.tcc.model.Grupo;
import com.agon.tcc.model.Partida;
import com.agon.tcc.model.Resultado;
import com.agon.tcc.repository.CampeonatoRepository;
import com.agon.tcc.repository.EquipeGrupoRepository;
import com.agon.tcc.repository.EquipeRepository;
import com.agon.tcc.repository.GrupoRepository;
import com.agon.tcc.repository.PartidaRepository;

@Service
public class PartidaService {
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private CampeonatoRepository campeonatoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private EquipeGrupoRepository equipeGrupoRepository;
	
	@Autowired
	private EquipeService equipeService;

    @Autowired
    private ResultadoService resultadoService;

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
    
    public List<PartidaDTO> findPartidasByCampeonato(Long idCampeonato) {
        return partidaRepository.findPartidasByCampeonato(idCampeonato)
                .stream()
                .map(p -> new PartidaDTO(p.getId(), p.getDataPartida(), p.getEndereco(), p.getEtapaCampeonato(), p.getGrupo(), p.getDadosPartidas(), p.getAmistoso(), p.getCampeonato()))
                .collect(Collectors.toList());
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
    public void gerarPartidasPontosCorridos(Campeonato campeonato, EtapaCampeonato etapa, List<Equipe> equipesCamp, Endereco enderecoDefault, int rodada) {
        int totalEquipes = equipesCamp.size();
        int maxPartidasPorRodada = 6;
                
        List<Partida> partidas = new ArrayList<>();

        // Gerando todas as partidas de ida
        for (int i = 0; i < totalEquipes; i++) {
            for (int j = i + 1; j < totalEquipes; j++) {
                Partida partida = new Partida();
                partida.setDataPartida(LocalDateTime.now().plusDays(3 * rodada));
                partida.setEtapaCampeonato(etapa);
                partida.setEndereco(enderecoDefault);
                partida.setCampeonato(campeonato);

                DadosPartida dadosPartida1 = new DadosPartida();
                dadosPartida1.setEquipe(equipesCamp.get(i));
                dadosPartida1.setPartida(partida);
                dadosPartida1.setPlacar(0);
                dadosPartida1.setQtdeCartaoVermelho(0);
                dadosPartida1.setQtdeCartaoAmarelo(0);
                dadosPartida1.setPenaltis(0);

                DadosPartida dadosPartida2 = new DadosPartida();
                dadosPartida2.setEquipe(equipesCamp.get(j));
                dadosPartida2.setPartida(partida);
                dadosPartida2.setPlacar(0);
                dadosPartida2.setQtdeCartaoVermelho(0);
                dadosPartida2.setQtdeCartaoAmarelo(0);
                dadosPartida2.setPenaltis(0);

                partida.setDadosPartidas(Arrays.asList(dadosPartida1, dadosPartida2));
                partidas.add(partida);

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

                if (partidas.size() % maxPartidasPorRodada == 0) {
                    rodada++;
                }
            }
        }

        // Salvando todas as partidas após a distribuição das rodadas
        for (Partida partida : partidas) {
            partidaRepository.save(partida);
        }
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
    
    public void gerarPrimeiraRodada(Campeonato campeonato, EtapaCampeonato etapaCampeonato, Endereco endereco) {
    	int qtdadePartidasIniciais = campeonato.getQuantidadeEquipes()/2;
    	
    	List<Equipe> equipesParticipantes = this.equipeService.findAllEquipesByIdCampeonato(campeonato.getId());
    	List<Equipe> grupoUm = equipesParticipantes.subList(0, qtdadePartidasIniciais);
    	List<Equipe> grupoDois = equipesParticipantes.subList(qtdadePartidasIniciais, equipesParticipantes.size());
    	
    	for(int i = 0; i < qtdadePartidasIniciais; i++) {
    		
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
    
	public void gerarFaseDeGrupos(Endereco endereco, Long idCampeonato) {
		Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);
		List<Equipe> equipes = equipeRandomica(equipeRepository.findAllTimesByIdCampeonato(idCampeonato));
		try {
			if (campeonato.isPresent()) {
				Campeonato camp = campeonato.get();
				Integer quantidadePorGrupos = 4;
				Integer quantidadeGrupos = camp.getQuantidadeEquipes() / 4;
				if (camp.getQuantidadeEquipes() == 12) {
					quantidadePorGrupos = 3;
				}
				List<Grupo> grupos = this.montarGrupos(quantidadePorGrupos, quantidadeGrupos, equipes, camp);
				this.gerarPartidasFaseGrupo(grupos, endereco, quantidadeGrupos);
			}
		} catch (RuntimeException error) {
			throw new RuntimeException(error);
		}
	}
	
	public List<Equipe> equipeRandomica(List<Equipe> equipes) {
		for (int i = 0; i < equipes.size(); i++) {
			int random = (int) (Math.random() * equipes.size());
			Equipe aux = equipes.get(i);
			equipes.set(i, equipes.get(random));
			equipes.set(random, aux);
		}
		return equipes;
	}

	public List<Grupo> montarGrupos(int quantidadePorGrupos, int quantidadeGrupos, List<Equipe> equipes, Campeonato campeonato) {
		List<Grupo> grupos = new ArrayList<>();
		char grupoNome = 'A';

		for (int i = 0; i < quantidadeGrupos; i++) {
			Grupo grupo = new Grupo();
			grupo.setCampeonato(campeonato);
			grupo.setNome("Grupo " + grupoNome);
			grupoRepository.save(grupo);

			List<EquipeGrupo> equipesGrupos = new ArrayList<>();
			for (int j = 0; j < quantidadePorGrupos; j++) {
				EquipeGrupo equipeGrupo = new EquipeGrupo();
				equipeGrupo.setEquipe(equipes.get(i * quantidadePorGrupos + j));
				equipeGrupo.setGrupo(grupo);
				equipeGrupo.setPontos(0);
				equipeGrupo.setQtdJogos(0);
				equipeGrupo.setVitorias(0);
				equipeGrupo.setEmpates(0);
				equipeGrupo.setDerrotas(0);
				equipesGrupos.add(equipeGrupo);
				this.equipeGrupoRepository.save(equipeGrupo); // Salva cada equipe do grupo
			}

			grupo.setEquipesGrupos(equipesGrupos);
			grupo.setTotalJogos(calcularJogos(quantidadePorGrupos));
			this.grupoRepository.save(grupo); // Atualiza o grupo com as equipes
			grupos.add(grupo);
			grupoNome++; // Incrementa a letra do grupo (de 'A' para 'B', etc.)
		}

		return grupos;
	}

	public void gerarPartidasFaseGrupo(List<Grupo> grupos, Endereco endereco, int quantidadeRodadas) {
		EtapaCampeonato etapaCampeonato = new EtapaCampeonato();
		etapaCampeonato.setCampeonato(grupos.get(0).getCampeonato());
		etapaCampeonato.setNomeEtapa("Fase de Grupos");
		etapaCampeonato.setTotalRodadas(quantidadeRodadas);
		this.etapaCampeonatoService.create(etapaCampeonato);
		for (Grupo grupo : grupos) {
			List<EquipeGrupo> equipesGrupos = grupo.getEquipesGrupos();
			for (int i = 0; i < equipesGrupos.size(); i++) {
				for (int j = i + 1; j < equipesGrupos.size(); j++) {
					Partida partida = new Partida();
					partida.setGrupo(grupo);
					// partida.setDataPartida(...);
					partida.setEndereco(endereco);
					List<DadosPartida> dadosPartidas = new ArrayList<>();
					DadosPartida dadosPartida1 = new DadosPartida();
					dadosPartida1.setEquipe(equipesGrupos.get(i).getEquipe());
					dadosPartida1.setPartida(partida);
					dadosPartida1.setPlacar(0);
					dadosPartida1.setQtdeCartaoAmarelo(0);
					dadosPartida1.setQtdeCartaoVermelho(0);
					dadosPartida1.setPenaltis(0);
					dadosPartida1.setDadosAtualizados(false);
					dadosPartidas.add(dadosPartida1);

					DadosPartida dadosPartida2 = new DadosPartida();
					dadosPartida2.setEquipe(equipesGrupos.get(j).getEquipe());
					dadosPartida2.setPartida(partida);
					dadosPartida2.setPlacar(0);
					dadosPartida2.setQtdeCartaoAmarelo(0);
					dadosPartida2.setQtdeCartaoVermelho(0);
					dadosPartida2.setPenaltis(0);
					dadosPartida2.setDadosAtualizados(false);
					dadosPartidas.add(dadosPartida2);
					partida.setDadosPartidas(dadosPartidas);
					partida.setCampeonato(grupo.getCampeonato());

					partida.setEtapaCampeonato(etapaCampeonato);
					this.create(partida); // Salva a partida
				}
			}
		}

	}

	public int calcularJogos(int numeroEquipesGrupo) {
		return (numeroEquipesGrupo * (numeroEquipesGrupo - 1)) / 2;
	}
    
}
