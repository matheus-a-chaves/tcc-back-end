package com.agon.tcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Resultado;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

	@Query(value = "SELECT r.* FROM Resultado r WHERE r.etapa_campeonato_id = :idEtapaCampeonato", nativeQuery  = true)
	List<Resultado> findAllByIdEtapaCampeonato(Long idEtapaCampeonato);
	
}
