package com.agon.tcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {

	@Query("SELECT DISTINCT c FROM Campeonato c JOIN c.usuarios u JOIN c.modalidade m WHERE u.id = :usuarioId AND m.id = :modalidadeId")
    List<Campeonato> findByUsuariosIdAndModalidadeId(Long usuarioId, Long modalidadeId);
	
}
