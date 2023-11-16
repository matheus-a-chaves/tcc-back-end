package com.agon.tcc.service;

import com.agon.tcc.model.entity.Modalidade;
import com.agon.tcc.model.persistence.ModalidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class ModalidadeService {
    private final ModalidadeRepository repository;
    public List<Modalidade> findAll(){
        return repository.findAll();
    }
}
