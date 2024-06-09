package com.agon.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Classificacao;

@Repository
public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

}
