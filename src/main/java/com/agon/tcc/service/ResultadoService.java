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
	
//	public void updateResultadosPartidaAtualizada() {
//		for (int i = 0; i < dadosPartidasDTO.size(); i++) {            
//	        // Atualizar o resultado associado
//	        Optional<Resultado> optResultado = resultadoRepository.findByEquipePartida(equipeId, partidaId);
//	        if (optResultado.isPresent()) {
//	            Resultado resultado = optResultado.get();
//	            
//	            Optional<DadosPartida> optDadoPartida2 = dadosPartidaRepository.findByEquipePartida(dadosPartidasDTO.get(i+1).equipeId(), partidaId);
//	            if (optDadoPartida2.isPresent()) {
//	            	DadosPartida dadosPartida2 = optDadoPartida2.get();
//	            	
//	            }
//	            resultadoRepository.save(resultado);
//	        }
//		}
//	}
		
	public Resultado calcularResultados(Resultado resultadoAtual, int golsMarcados, int golsSofridos) {
	    int vitorias = resultadoAtual.getVitorias();
	    int empates = resultadoAtual.getEmpates();
	    int derrotas = resultadoAtual.getDerrotas();
	    
	    if (golsMarcados > golsSofridos) {
	        vitorias++;
	    }
	    if (golsMarcados == golsSofridos) {
	        empates++;
	    }
	    if (golsMarcados < golsSofridos) {
	        derrotas++;
	    }
	    String novoSaldoGols = calcularSaldoGols(resultadoAtual, golsMarcados, golsSofridos);
	    
	    resultadoAtual.setVitorias(vitorias);
	    resultadoAtual.setEmpates(empates);
	    resultadoAtual.setDerrotas(derrotas);
	    resultadoAtual.setSaldoGols(novoSaldoGols);
	    // Calcular pontos (exemplo: 3 pontos por vitória, 1 ponto por empate)
	    int pontos = vitorias * 3 + empates;
	    resultadoAtual.setPontos(pontos);
	    
	    return resultadoAtual;
	}
	
	public String calcularSaldoGols(Resultado resultadoAtual, int golsMarcados, int golsSofridos) {
	    int saldo = Integer.parseInt(resultadoAtual.getSaldoGols().substring(1));
	    
	    if (resultadoAtual.getSaldoGols().charAt(0) == '-') {
	        saldo *= -1; // Se for negativo, multiplicar por -1
	    } else if (resultadoAtual.getSaldoGols().charAt(0) == '+') {
	        saldo *= 1; // Se for positivo, multiplicar por 1 (ou seja, não altera)
	    }
	    
	    int novoSaldo = saldo + golsMarcados - golsSofridos;
	    
	    if (novoSaldo < 0) {
	        return "-" + Math.abs(novoSaldo);
	    } else if (novoSaldo > 0) {
	        return "+" + novoSaldo;
	    } else {
	        return "0";
	    }
	}

}
