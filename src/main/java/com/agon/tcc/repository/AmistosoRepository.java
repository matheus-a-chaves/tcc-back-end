package com.agon.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agon.tcc.model.Amistoso;

@Repository
public interface AmistosoRepository extends JpaRepository<Amistoso, Long> {

}
