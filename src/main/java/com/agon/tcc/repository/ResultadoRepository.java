package com.agon.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Resultado;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

}
