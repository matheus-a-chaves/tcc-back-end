package com.agon.tcc.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.PartidaDTO;
import com.agon.tcc.dto.ResultadoDTO;
import com.agon.tcc.dto.ResultadoResponseDTO;
import com.agon.tcc.model.DadosPartida;
import com.agon.tcc.model.Equipe;
import com.agon.tcc.model.Partida;
import com.agon.tcc.model.Resultado;
import com.agon.tcc.repository.DadosPartidaRepository;
import com.agon.tcc.repository.PartidaRepository;
import com.agon.tcc.repository.ResultadoRepository;

@Service
public class ResultadoService {
	
	@Autowired
	private ResultadoRepository resultadoRepository;
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private DadosPartidaRepository dadosPartidaRepository;
		
	public List<ResultadoDTO> findAll() {
        return resultadoRepository.findAll()
                .stream()
                .map(c -> new ResultadoDTO(c.getId(), c.getVitorias(), c.getEmpates(), c.getDerrotas(), c.getSaldoGols(), c.getPontos(), c.getRodada()))
                .collect(Collectors.toList());
    }

    public ResultadoDTO findById(Long id) {
        Optional<Resultado> resultado = resultadoRepository.findById(id);
        if (resultado.isPresent()) {
            Resultado c = resultado.get();
            return new ResultadoDTO(c.getId(), c.getVitorias(), c.getEmpates(), c.getDerrotas(), c.getSaldoGols(), c.getPontos(), c.getRodada());
        }
        return null;
    }
    
	public List<Resultado> findAllByIdEtapaCampeonato(Long idEtapaCampeonato) {
        return resultadoRepository.findAllByIdEtapaCampeonato(idEtapaCampeonato);
    }
	
    @Transactional
    public void update(ResultadoDTO resultadoDTO, Long idEquipe, Long idPartida) {
        Optional<Resultado> optPartida = resultadoRepository.findByEquipePartida(idEquipe, idPartida);
        if (optPartida.isPresent()) {
            Resultado resultado = optPartida.get();
            resultado.setVitorias(resultadoDTO.vitorias());
            resultado.setEmpates(resultadoDTO.empates());
            resultado.setDerrotas(resultadoDTO.derrotas());
            resultado.setSaldoGols(resultadoDTO.saldoGols());
            resultado.setPontos(resultadoDTO.pontos());
            resultado.setRodada(resultadoDTO.rodada());
            
            try {
            	resultadoRepository.save(resultado);
            } catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível atualizar os dados da equipe " + optPartida.get().getDadosPartida().getEquipe().getNome() + " na partida " + idPartida);
			}
        }
    }
	
	@Transactional
	public void create(Resultado resultado) {
		resultadoRepository.save(resultado);
	}
		
	public void delete(Long id) {
		if (findById(id) != null) {
			try {
				this.resultadoRepository.deleteById(id);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Não foi possível excluir pois há entidades relacionadas!");
			}
		}
	}
	
//	public List<ResultadoResponseDTO> findAllByCampeonatoPontosCorridos(Long idCampeonato) {
//	    List<Partida> partidas = partidaRepository.findByCampeonato(idCampeonato);
//	    Map<Long, Resultado> classificacao = new HashMap<>();
//
//	    for (PartidaDTO partida : partidas) {
//	        List<Resultado> resultadosPartida = resultadoRepository.findByPartida(partida.getId());
//
//	        for (Resultado resultado : resultadosPartida) {
//	            DadosPartida dp1 = resultado.getDadosPartida();
//	            DadosPartida dp2 = resultado.getDadosPartida();
//	            Equipe e1 = dp1.getEquipe();
//	            Equipe e2 = dp2.getEquipe();
//
//	            atualizarClassificacao(classificacao, dp1, dp2, e1, e2);
//	            atualizarClassificacao(classificacao, dp2, dp1, e2, e1);
//	        }
//	    }
//
//	    List<Resultado> classificacoes = new ArrayList<>(classificacao.values());
//	    classificacoes.sort(Comparator.comparingInt(Resultado::getPontos).reversed()
//	            .thenComparing(Resultado::getSaldoGols, Comparator.reverseOrder())
//	            .thenComparing(Resultado::getRodada));
//
//	    return classificacoes.stream()
//	            .map(this::toResultadoResponseDTO)
//	            .collect(Collectors.toList());
//	}
	
//	private void atualizarClassificacao(Map<Long, Resultado> classificacao, DadosPartida dp1, DadosPartida dp2, Equipe e1, Equipe e2) {
//	    Resultado result = classificacao.getOrDefault(e1.getId(), new Resultado());
//
//	    result.setJogos(result.getJogos() + 1);
//	    result.setSaldoGols(result.getSaldoGols() + calcularSaldoGols(dp1, dp2));
//
//	    int placarEquipe1 = dp1.getPlacar() == null ? 0 : dp1.getPlacar();
//	    int placarEquipe2 = dp2.getPlacar() == null ? 0 : dp2.getPlacar();
//	    int penaltisEquipe1 = dp1.getPenaltis() == null ? 0 : dp1.getPenaltis();
//	    int penaltisEquipe2 = dp2.getPenaltis() == null ? 0 : dp2.getPenaltis();
//
//	    int pontuacaoEquipe1, pontuacaoEquipe2;
//
//	    if (placarEquipe1 > placarEquipe2) {
//	        pontuacaoEquipe1 = 3;
//	    } else if (placarEquipe1 < placarEquipe2) {
//	        pontuacaoEquipe2 = 3;
//	    } else { // Empate no placar
//	        if (penaltisEquipe1 > penaltisEquipe2) {
//	            pontuacaoEquipe1 = 3;
//	        } else if (penaltisEquipe1 < penaltisEquipe2) {
//	            pontuacaoEquipe2 = 3;
//	        } else { // Empate no placar e nos pênaltis
//	            pontuacaoEquipe1 = 1;
//	            pontuacaoEquipe2 = 1;
//	        }
//	    }
//
//	    result.setPontos(result.getPontos() + pontuacaoEquipe1);
//	    result.setVitorias(result.getVitorias() + (pontuacaoEquipe1 == 3 ? 1 : 0));
//	    result.setEmpates(result.getEmpates() + (pontuacaoEquipe1 == 1 ? 1 : 0));
//	    result.setDerrotas(result.getDerrotas() + (pontuacaoEquipe1 == 0 ? 1 : 0));
//
//	    Resultado resultEquipe2 = classificacao.getOrDefault(e2.getId(), new Resultado());
//	    resultEquipe2.setJogos(resultEquipe2.getJogos() + 1);
//	    resultEquipe2.setSaldoGols(resultEquipe2.getSaldoGols() - calcularSaldoGols(dp1, dp2));
//	    resultEquipe2.setPontos(resultEquipe2.getPontos() + pontuacaoEquipe2);
//	    resultEquipe2.setVitorias(resultEquipe2.getVitorias() + (pontuacaoEquipe2 == 3 ? 1 : 0));
//	    resultEquipe2.setEmpates(resultEquipe2.getEmpates() + (pontuacaoEquipe2 == 1 ? 1 : 0));
//	    resultEquipe2.setDerrotas(resultEquipe2.getDerrotas() + (pontuacaoEquipe2 == 0 ? 1 : 0));
//
//	    classificacao.put(e1.getId(), result);
//	    classificacao.put(e2.getId(), resultEquipe2);
//	}
	
//	public List<ResultadoResponseDTO> findAllByCampeonatoPontosCorridos(Long idCampeonato) {
//        List<Partida> partidas = partidaRepository.findByCampeonato(idCampeonato);
//        Map<Long, Resultado> classificacaoMap = new HashMap<>();
//
//        for (Partida partida : partidas) {
//            List<DadosPartida> dadosPartidas = dadosPartidaRepository.findByPartida(partida.getId());
//
//            if (dadosPartidas.size() != 2) continue;
//
//            DadosPartida dp1 = dadosPartidas.get(0);
//            DadosPartida dp2 = dadosPartidas.get(1);
//
//            atualizaClassificacao(classificacaoMap, dp1, dp2);
//            atualizaClassificacao(classificacaoMap, dp2, dp1);
//        }
//
//        List<Resultado> classificacoes = new ArrayList<>(classificacaoMap.values());
//        classificacoes.sort(Comparator.comparingInt(Resultado::getPontos));
//
//        return classificacoes.stream()
//                .map(r -> new ResultadoResponseDTO())
//                .collect(Collectors.toList());
//    }
//
//    public void atualizaClassificacao(Map<Long, Resultado> reMap, DadosPartida dadosPartida1, DadosPartida dadosPartida2) {
//        Resultado result = classificacaoMap.getOrDefault(dadosPartida1.getEquipe(), new Resultado());
//        
//        result.setJogos(result.getJogos() + 1);
//        result.setSaldoGols(result.getSaldoGols() + (dadosPartida1.getPlacar() - dadosPartida2.getPlacar()));
//
//        if (dadosPartida1.getPlacar() > dadosPartida2.getPlacar()) {
//        	result.setPontos(result.getPontos() + 3);
//        	result.setVitorias(result.getVitorias() + 1);
//        } else if (dadosPartida1.getPlacar() == dadosPartida2.getPlacar()) {
//        	result.setPontos(result.getPontos() + 1);
//        	result.setEmpates(result.getEmpates() + 1);
//        } else {
//        	result.setDerrotas(result.getDerrotas() + 1);
//        }
//
//        classificacaoMap.put(dadosPartida1.getEquipe(), result);
//    }

}
