package com.agon.tcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

//	@Query("SELECT p FROM Partida p WHERE p.campeonato.id = :campeonatoId")
//    List<Partida> findByCampeonato(@Param("campeonatoId") Long campeonatoId);

	@Query(value = "SELECT p FROM partida p LEFT JOIN amistoso a ON p.amistoso_id = a.id LEFT JOIN dados_partida dp on p.id = dp.partida_id " +
		   " WHERE a.status = 'PENDENTE' " +
		   "   AND dp.equipe_id in (SELECT DISTINCT m.id_equipe " +
		   "  						  FROM membros m " +
		   "						 WHERE m.id_atletica = :atleticaId)", nativeQuery = true)
	List<Partida> buscarPartidasAtletica(Long atleticaId);
	
}