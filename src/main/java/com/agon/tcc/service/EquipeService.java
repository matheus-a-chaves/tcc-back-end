package com.agon.tcc.service;

import com.agon.tcc.model.entity.Equipe;
import com.agon.tcc.model.entity.Usuario;
import com.agon.tcc.model.persistence.EquipeRepository;
import com.agon.tcc.model.persistence.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EquipeService {
    private final EquipeRepository repository;
    private final Logger logger = Logger.getLogger(AmistosoService.class.getName());
    public void create(Equipe equipe){
        logger.info("Criando time");

        repository.save(equipe);

        logger.info("Time criado com sucesso");
    }

    public Optional<Equipe> findById(Long id){
        return repository.findById(id);
    }

    public List<Equipe> findAll(){
        return repository.findAll();
    }
    public List<Equipe> findAllByIdUser(Long id){
        return repository.findAllByIdUser(id);
    }

    public List<Equipe> findAllByIdModal(Long idUsuario, Long idModalidde){
        return repository.findAllByIdModal(idUsuario, idModalidde);
    }

}
