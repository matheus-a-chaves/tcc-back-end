package com.agon.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.EquipeGrupo;

@Repository
public interface EquipeGrupoRepository extends JpaRepository<EquipeGrupo, Long> {

}
