package com.agon.tcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agon.tcc.model.Membro;

public interface MembroRepository extends JpaRepository<Membro, Long>{

	Optional<Membro> findByIdEquipeAndIdAtletica(Long idEquipe, Long idAtletica);

}
