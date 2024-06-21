package com.agon.tcc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agon.tcc.dto.ResultadoDTO;
import com.agon.tcc.model.Resultado;
import com.agon.tcc.repository.ResultadoRepository;

@Service
public class ResultadoService {
	
	@Autowired
	private ResultadoRepository resultadoRepository;

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

//	public List<ResultadoDTO> findByEquipe(Long id) {
//		return resultadoRepository.findByEquipe(id)
//				.stream()
//				.map(c -> new ResultadoDTO(c.getId(), c.getVitorias(), c.getEmpates(), c.getDerrotas(), c.getSaldoGols(), c.getPontos(), c.getRodada()))
//				.collect(Collectors.toList());
//	}
//
//	public List<ResultadoDTO> findByPartida(Long id) {
//		return resultadoRepository.findByPartida(id)
//				.stream()
//				.map(dpp -> new ResultadoDTO(dpp.getId(), dpp.getPlacar(), dpp.getQtdeCartaoVermelho(), dpp.getQtdeCartaoAmarelo(), 
//												dpp.getPenaltis(), dpp.getEquipe().getId(), dpp.getPartida().getId()))
//				.collect(Collectors.toList());
//	}
//	
//	public ResultadoDTO findByEquipePartida(Long equipeId, Long partidaId) {
//        Optional<Resultado> resultado = resultadoRepository.findByEquipePartida(equipeId, partidaId);
//        if (resultado.isPresent()) {
//            Resultado c = resultado.get();
//            return new ResultadoDTO(c.getId(), c.getVitorias(), c.getEmpates(), c.getDerrotas(), c.getSaldoGols(), c.getPontos(), c.getRodada());
//        }
//        return null;
//    }

    @Transactional
    public void update(ResultadoDTO resultadoDTO, Long equipeId) {//, Long partidaId
//        Optional<Resultado> optPartida = resultadoRepository.findByEquipePartida(equipeId, partidaId);
//        if (optPartida.isPresent()) {
//            Resultado resultado = optPartida.get();
//            resultado.setPlacar(resultadoDTO.placar());
//            resultado.setQtdeCartaoVermelho(resultadoDTO.qtdeCartaoVermelho());
//            resultado.setQtdeCartaoAmarelo(resultadoDTO.qtdeCartaoAmarelo());
//            resultado.setPenaltis(resultadoDTO.penaltis());
//            
//            try {
//            	resultadoRepository.save(resultado);
//            } catch (Exception e) {
//				throw new RuntimeErrorException(null, "Não foi possível atualizar os dados da equipe " + resultado.getEquipe().getNome() + 
//											" na partida " + resultado.getPartida().getId());
//			}
//        }
    }
	
	@Transactional
	public void create(ResultadoDTO resultadoDTO) {
		resultadoRepository.save(new Resultado(resultadoDTO));
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
}
