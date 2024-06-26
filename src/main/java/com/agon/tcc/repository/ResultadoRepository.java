package com.agon.tcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Resultado;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

	@Query(value = "SELECT r.* FROM Resultado r WHERE r.etapa_campeonato_id = :idEtapaCampeonato", nativeQuery  = true)
	List<Resultado> findAllByIdEtapaCampeonato(Long idEtapaCampeonato);
	
//	@Query(value = "SELECT r.* FROM resultado r JOIN dados_partida dp ON r.dados_partida_id = dp.id JOIN partida p ON dp.partida_id = p.id WHERE dp.partida_id = :idPartida", nativeQuery = true)
//	List<Resultado> findByPartida(@Param(":idPartida") Long idPartida);
	
	@Query(value = "SELECT r.* FROM resultado r JOIN dados_partida dp ON r.dados_partida_id = dp.id JOIN partida p ON dp.partida_id = p.id WHERE dp.partida_id = :idPartida AND dp.equipe_id = :idEquipe", nativeQuery = true)
	Optional<Resultado> findByEquipePartida(Long idEquipe, Long idPartida);
}
