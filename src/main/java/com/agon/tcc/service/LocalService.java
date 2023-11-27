package com.agon.tcc.service;

import com.agon.tcc.model.entity.Local;
import com.agon.tcc.model.persistence.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class LocalService {
    private final LocalRepository repository;
    private final Logger logger = Logger.getLogger(AmistosoService.class.getName());
    public void create(Local local){
        logger.info("Criando local");

        repository.save(local);

        logger.info("Local criado com sucesso");
    }

    public Optional<Local> findById(Long id){
        return repository.findById(id);
    }

    public List<Local> findAll(){
        return repository.findAll();
    }

}
