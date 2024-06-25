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
//    List<Partida> findByCampeonato(@Param("campeonatoId") Long campeonatoId);

	@Query(value = "SELECT p.* FROM partida p LEFT JOIN amistoso a ON p.amistoso_id = a.id \n" +
			"LEFT JOIN dados_partida dp on p.id = dp.partida_id WHERE a.status = 'PENDENTE' \n" +
			"AND dp.equipe_id in ( SELECT DISTINCT m.id_equipe FROM membros m WHERE m.id_atletica=:atleticaId)", nativeQuery = true)
	List<Partida> buscarPartidasAtletica(Long atleticaId);

//  List<Partida> findByCampeonato(@Param("campeonatoId") Long campeonatoId);

	@Query(value ="SELECT p.* FROM partida p INNER JOIN dados_partida dp ON p.id = dp.partida_id JOIN Amistoso a ON a.id = p.amistoso_id WHERE date(p.data_partida) = :data AND dp.equipe_id = :idEquipe AND a.status = 'CONFIRMADO'", nativeQuery = true)
	List<Partida> encontrarPartidasPorData(@Param("data") LocalDate data, Long idEquipe);

	@Query(value ="SELECT p.* FROM partida p INNER JOIN dados_partida dp ON p.id = dp.partida_id JOIN Amistoso a ON a.id = p.amistoso_id WHERE dp.equipe_id = :idEquipe AND a.status = 'CONFIRMADO'", nativeQuery = true)
	List<Partida> findAmistososByEquipe(@Param("idEquipe") Long idEquipe);
	
	@Query(value = "SELECT p.* FROM partida p WHERE p.campeonato_id = :idCampeonato", nativeQuery = true)
	List<Partida> findPartidasByCampeonato(Long idCampeonato);
}