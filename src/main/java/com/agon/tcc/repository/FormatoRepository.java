package com.agon.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Formato;

@Repository
public interface FormatoRepository extends JpaRepository<Formato, Long> {

}
