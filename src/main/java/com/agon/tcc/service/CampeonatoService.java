package com.agon.tcc.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import com.agon.tcc.model.*;
import com.agon.tcc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.CampeonatoDTO;
import com.agon.tcc.dto.CampeonatoUsuarioDTO;
import com.agon.tcc.dto.EquipeDTO;
import com.agon.tcc.dto.EtapaCampeonatoDTO;
import com.agon.tcc.util.Util;

@Service
public class CampeonatoService {

	@Autowired
	private CampeonatoRepository campeonatoRepository;
	
	@Autowired
    private EquipeService equipeService;

    @Autowired
    private EtapaCampeonatoService etapaCampeonatoService;

    @Autowired
    private PartidaService partidaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ResultadoService resultadoService;
    
    @Autowired
    private CampeonatoUsuarioService campeonatoUsuarioService;
    
    @Autowired
    private DadosPartidaService dadosPartidaService;

	@Autowired
	private MembroRepository membroRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private EquipeGrupoRepository equipeGrupoRepository;

	private CampeonatoDTO converteDados(Campeonato camp) throws Exception {
        return new CampeonatoDTO(camp.getId(), camp.getNome(), camp.getQuantidadeEquipes(), camp.getDataInicio(), camp.getDataFim(), 
        						 Util.convertToString(camp.getRegulamento()), Util.convertToString(camp.getImagemCampeonato()), 
        						 camp.getFormato(), camp.getModalidade());
    }
		
	public List<CampeonatoDTO> findAll() {
		return campeonatoRepository.findAll()
				.stream()
				.map(c -> {
					try {
						return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), 
												 Util.convertToString(c.getRegulamento()), Util.convertToString(c.getImagemCampeonato()), c.getFormato(), c.getModalidade());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), null, null, c.getFormato(), c.getModalidade());
				}) 
				.collect(Collectors.toList());
	}
	
	public CampeonatoDTO findById(Long id) {
		Optional<Campeonato> campeonato = campeonatoRepository.findById(id);
		if (campeonato.isPresent()) {
			Campeonato c = campeonato.get();
			try {
				return converteDados(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public List<CampeonatoDTO> findByUsuarioIdAndModalidadeId(Long usuarioId, Long modalidadeId) {
		return campeonatoRepository.findByUsuariosIdAndModalidadeId(usuarioId, modalidadeId)
				.stream()
                .map(c -> {
                    try {
                        return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), 
                                                 Util.convertToString(c.getRegulamento()), Util.convertToString(c.getImagemCampeonato()), c.getFormato(), c.getModalidade());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), null, null, c.getFormato(), c.getModalidade());
                }) 
                .collect(Collectors.toList());
    }
	
	@Transactional
	public void create(CampeonatoDTO campeonatoDTO, Long idUsuario) {
		Campeonato campeonato = campeonatoRepository.save(new Campeonato(campeonatoDTO));
		CampeonatoUsuario campeonatoUsuario = new CampeonatoUsuario();
		campeonatoUsuario.setIdCampeonato(campeonato.getId());
		campeonatoUsuario.setIdCriador(idUsuario);
		campeonatoUsuarioService.create(campeonatoUsuario);
	}
	
	@Transactional
	public void adicionarEquipe(String cnpjAtletica, Long idCampeonato, Long idModalidade) {
		Usuario usuario = this.usuarioService.findByCnpj(cnpjAtletica);
		EquipeDTO equipe =  this.equipeService.findByAtleticaAndModalidade(usuario.getId(), idModalidade);
		if(equipe == null) {
			throw new RuntimeErrorException(null, "A atlética não possui um time da modalidade do campeonato!");
		}
		
		CampeonatoUsuario campeonatoUsuarioAux = this.campeonatoUsuarioService.findByIdCampeonatoAndIdAtletica(idCampeonato, usuario.getId());
		if(campeonatoUsuarioAux != null) {
			throw new RuntimeErrorException(null, "Equipe já adicionada.");
		}
		
		CampeonatoUsuario campeonatoUsuario = new CampeonatoUsuario(idCampeonato, usuario.getId());
		this.campeonatoUsuarioService.create(campeonatoUsuario);
	}
	
	@Transactional
	public void removerEquipe(Long idCampeonato, Long idEquipe) {
		Usuario atletica = this.usuarioService.findByEquipe(idEquipe);
		CampeonatoUsuario campeonatoUsuarioAux = this.campeonatoUsuarioService.findByIdCampeonatoAndIdAtletica(idCampeonato, atletica.getId());
		campeonatoUsuarioService.delete(campeonatoUsuarioAux.getId());
	}
	
	@Transactional
	public void update(CampeonatoDTO campeonatoDTO) {
		Campeonato novoCamp = new Campeonato(findById(campeonatoDTO.id()));
		novoCamp.setNome(campeonatoDTO.nome());
		novoCamp.setDataInicio(campeonatoDTO.dataInicio());
		novoCamp.setDataFim(campeonatoDTO.dataFim());
		novoCamp.setQuantidadeEquipes(campeonatoDTO.quantidadeEquipes());
		this.campeonatoRepository.save(novoCamp);
	}
	
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.campeonatoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não é possível excluir pois há entidades relacionadas!");
			}
		}
	}

	public List<CampeonatoDTO> findAllIntByModalidadeId(Long equipeId, Long modalidadeId) {
		Long usuarioId = membroRepository.findIdAtleticaByIdEquipe(equipeId);

		return campeonatoRepository.findAllIntByModalidadeId(usuarioId, modalidadeId)
				.stream()
				.map(c -> {
					try {
						return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(),
								Util.convertToString(c.getRegulamento()), Util.convertToString(c.getImagemCampeonato()), c.getFormato(), c.getModalidade());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), null, null, c.getFormato(), c.getModalidade());
				})
				.collect(Collectors.toList());
	}

	public List<CampeonatoDTO> findAllExtByModalidadeId(Long equipeId, Long modalidadeId) {
		Long usuarioId = membroRepository.findIdAtleticaByIdEquipe(equipeId);

		return campeonatoRepository.findAllExtByModalidadeId(usuarioId, modalidadeId)
				.stream()
				.map(c -> {
					try {
						return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(),
								Util.convertToString(c.getRegulamento()), Util.convertToString(c.getImagemCampeonato()), c.getFormato(), c.getModalidade());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new CampeonatoDTO(c.getId(), c.getNome(), c.getQuantidadeEquipes(), c.getDataInicio(), c.getDataFim(), null, null, c.getFormato(), c.getModalidade());
				})
				.collect(Collectors.toList());
	}



	/*
	 * Método para iniciar o Campeonato e gerar as partidas
	 */
    @Transactional
    public void iniciarCampeonato(Long campeonatoId) throws Exception {
//        Campeonato campeonato = campeonatoRepository.findById(campeonatoId).orElseThrow(() -> new Exception("Campeonato não encontrado"));
//        List<Equipe> equipes = equipeRepository.findByCampeonatoId(campeonatoId);
//        
//        if (equipes.size() != campeonato.getQuantidadeEquipes() || equipes.size() < 2) {
//            throw new IllegalStateException("Número de equipes cadastradas não corresponde ao número esperado.");
//        }
//
//        // Criando a etapa do campeonato
//        EtapaCampeonato etapa = new EtapaCampeonato();
//        if (campeonato.getFormato().getId() >= 4) {
//        	//Caso seja formato composto
//            etapa.setNomeEtapa("Fase de Grupos");
//        } else if (campeonato.getFormato().getId() == 1) {
//        	etapa.setNomeEtapa(campeonato.getFormato().getNome());
//        } else if (campeonato.getFormato().getId() == 2) {
//        	etapa.setNomeEtapa(campeonato.getFormato().getNome());
//        }
//        etapa.setCampeonato(campeonato);
//        etapaCampeonatoRepository.save(etapa);
//
//        // Gerando as partidas
//        for (int i = 0; i < equipes.size(); i++) {
//            for (int j = i + 1; j < equipes.size(); j++) {
//                Partida partida = new Partida();
//                partida.setDataPartida(LocalDate.now().plusDays(3));
//                partida.setEtapaCampeonato(etapa);
//                
//                DadosPartida dadosPartida1 = new DadosPartida();
//                dadosPartida1.setEquipe(equipes.get(i));
//                dadosPartida1.setPartida(partida);
//
//                DadosPartida dadosPartida2 = new DadosPartida();
//                dadosPartida2.setEquipe(equipes.get(j));
//                dadosPartida2.setPartida(partida);
//
//                partida.setDadosPartidas(Arrays.asList(dadosPartida1, dadosPartida2));
//
//                partidaRepository.save(partida);
//            }
//        }
    	
    	Endereco endereco = new Endereco();
    	endereco.setCep("12345");
    	endereco.setRua("Rua das Laranjeiras");
    	endereco.setNumero(10);
    	endereco.setBairro("Bairro de teste");
    	endereco.setCidade("Curitiba");
    	endereco.setEstado("PR");
    	
    	this.gerarPartidasEliminatoriaSimples(endereco, campeonatoId, 5);
    }
    
    @Transactional
    public void gerarPartidasEliminatoriaSimples(Endereco endereco, Long idCampeonato, Integer idRodada) throws Exception {
    	try {
    		Campeonato campeonato = campeonatoRepository.findById(idCampeonato).orElseThrow(() -> new Exception("Campeonato não encontrado"));
    		
        	if(endereco == null) {
        		throw new RuntimeErrorException(null, "Favor cadastrar um endereço para o campeonato!");
        	}
    		
    		if(campeonato.getQuantidadeEquipes() != campeonato.getUsuarios().size()) {
        		throw new RuntimeErrorException(null, "A quantidade de times inscritos é diferente do previsto para o campeonato!");
        	}
        	
        	if(idRodada == 1) {
        		
        		int totalRodadas = this.totalRodadas(campeonato.getQuantidadeEquipes());
        		EtapaCampeonato etapaCampeonato = new EtapaCampeonato(campeonato.getFormato().getNome(), campeonato, totalRodadas);
            	etapaCampeonato = etapaCampeonatoService.create(etapaCampeonato);
        		
        		this.gerarPrimeiraRodada(campeonato, idCampeonato, etapaCampeonato, endereco);
        	} else {
        		this.gerarProximaRodada(campeonato, idCampeonato, idRodada);
        	}
        	
    	} catch(Exception ex) {
    		throw new RuntimeErrorException(null, ex.getMessage());
    	}
    }
    
    public void gerarProximaRodada(Campeonato campeonato, Long idCampeonato, Integer idRodada) {
    	
    	List<EtapaCampeonatoDTO> etapasCampeonato = this.etapaCampeonatoService.findByCampeonato(idCampeonato);
    	
    	Integer rodadaAtual = idRodada;
    	EtapaCampeonato etapaCampeonato = new EtapaCampeonato(etapasCampeonato.get(0));
    	
    	if(idRodada > etapaCampeonato.getTotalRodadas()) {
    		throw new RuntimeErrorException(null, "O campeonato não possui mais rodadas!");
    	}
    	
    	List<DadosPartida> dadosPartida = this.dadosPartidaService.findAllByRodadaCampeonato(idRodada - 1);
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
        		partidaService.create(partida);
        		
        		Resultado resultadoEquipe1 = new Resultado();
        		resultadoEquipe1.setDadosPartida(dadosPartida1);
        		resultadoEquipe1.setRodada(String.valueOf(rodadaAtual));
        		resultadoEquipe1.setVitorias(0);
        		resultadoEquipe1.setEmpates(0);
        		resultadoEquipe1.setDerrotas(0);
        		resultadoEquipe1.setSaldoGols("0");
        		resultadoEquipe1.setPontos(0);
        		resultadoEquipe1.setEtapaCampeonato(etapaCampeonato);
        		resultadoService.create(resultadoEquipe1);
        		
        		Resultado resultadoEquipe2 = new Resultado();
        		resultadoEquipe2.setDadosPartida(dadosPartida2);
        		resultadoEquipe2.setRodada(String.valueOf(rodadaAtual));
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
    
    public void gerarPrimeiraRodada(Campeonato campeonato, Long idCampeonato, EtapaCampeonato etapaCampeonato, Endereco endereco) {
    	int qtdadePartidasIniciais = campeonato.getQuantidadeEquipes()/2;
    	
    	List<Equipe> equipesParticipantes = this.equipeRepository.findAllTimesByIdCampeonato(idCampeonato);
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
    		partidaService.create(partida);
    		
    		Resultado resultadoEquipe1 = new Resultado();
    		resultadoEquipe1.setDadosPartida(dadosPartida1);
    		resultadoEquipe1.setRodada("1");
    		resultadoEquipe1.setVitorias(0);
    		resultadoEquipe1.setEmpates(0);
    		resultadoEquipe1.setDerrotas(0);
    		resultadoEquipe1.setSaldoGols("0");
    		resultadoEquipe1.setPontos(0);
    		resultadoEquipe1.setEtapaCampeonato(etapaCampeonato);
    		resultadoService.create(resultadoEquipe1);
    		
    		Resultado resultadoEquipe2 = new Resultado();
    		resultadoEquipe2.setDadosPartida(dadosPartida2);
    		resultadoEquipe2.setRodada("1");
    		resultadoEquipe2.setVitorias(0);
    		resultadoEquipe2.setEmpates(0);
    		resultadoEquipe2.setDerrotas(0);
    		resultadoEquipe2.setSaldoGols("0");
    		resultadoEquipe2.setPontos(0);
    		resultadoEquipe2.setEtapaCampeonato(etapaCampeonato);
    		resultadoService.create(resultadoEquipe2);
    	}
    }
    
    public int totalRodadas(int totalTimes) {
        int iteracoes = 0;
        while (totalTimes > 1) {
        	totalTimes /= 2;
            iteracoes++;
        }
        return iteracoes;
    }

	public List<CampeonatoDTO> gerarFaseDeGrupos(Endereco endereco, Long idCampeonato) {
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
				this.gerarPartidas(grupos, endereco, quantidadeGrupos);
			}
		} catch (RuntimeException error) {
			throw new RuntimeException(error);
		}
		return null;
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

	public void gerarPartidas(List<Grupo> grupos, Endereco endereco, int quantidadeRodadas) {
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
					this.partidaService.create(partida); // Salva a partida
				}
			}
		}

	}

	public int calcularJogos(int numeroEquipesGrupo) {
		return (numeroEquipesGrupo * (numeroEquipesGrupo - 1)) / 2;
	}
}
