package com.agon.tcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.CampeonatoUsuario;

@Repository
public interface CampeonatoUsuarioRepository extends JpaRepository<CampeonatoUsuario, Long>{
	
	Optional<CampeonatoUsuario> findByIdCampeonatoAndIdAtletica(Long idCampeonato, Long idAtletica);
}
