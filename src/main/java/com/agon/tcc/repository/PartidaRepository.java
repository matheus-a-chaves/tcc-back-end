package com.agon.tcc.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

//	@Query("SELECT p FROM Partida p WHERE p.campeonato.id = :campeonatoId")
//  List<Partida> findByCampeonato(@Param("campeonatoId") Long campeonatoId);
	
	@Query(value ="SELECT p.* FROM partida p INNER JOIN dados_partida dp ON p.id = dp.partida_id WHERE date(p.data_partida) = :data AND dp.equipe_id = :idEquipe", nativeQuery = true)
	List<Partida> encontrarPartidasPorData(@Param("data") LocalDate data, Long idEquipe);
}
