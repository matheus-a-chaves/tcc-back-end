package com.agon.tcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agon.tcc.model.DadosPartida;

public interface DadosPartidaRepository extends JpaRepository<DadosPartida, Long> {

	@Query("SELECT dp FROM DadosPartida dp WHERE dp.equipe.id = :equipeId")
	List<DadosPartida> findByEquipe(@Param("equipeId") Long equipeId);

	@Query("SELECT dp FROM DadosPartida dp WHERE dp.partida.id = :partidaId")
	List<DadosPartida> findByPartida(@Param("partidaId") Long partidaId);

	@Query("SELECT dp FROM DadosPartida dp WHERE dp.equipe.id = :equipeId AND dp.partida.id = :partidaId")
	Optional<DadosPartida> findByEquipePartida(@Param("equipeId") Long equipeId,@Param("partidaId") Long partidaId);

}
