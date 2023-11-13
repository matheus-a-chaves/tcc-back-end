package com.agon.tcc.service;

import com.agon.tcc.model.entity.Amistoso;
import com.agon.tcc.model.persistence.AmistosoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AmistosoService {

    private final AmistosoRepository repository;
    private Logger logger = Logger.getLogger(AmistosoService.class.getName());

    public Amistoso create(Amistoso amistoso){
        return repository.save(amistoso);
    }
}
