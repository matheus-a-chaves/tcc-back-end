package com.agon.tcc.service;

import com.agon.tcc.model.entity.Usuario;
import com.agon.tcc.model.persistence.ModalidadeRepository;
import com.agon.tcc.model.persistence.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final Logger logger = Logger.getLogger(AmistosoService.class.getName());
    public void create(Usuario usuario){
        logger.info("Salvando usuário");

        repository.save(usuario);

        logger.info("Usuário salvo com sucesso");
    }

    public Optional<Usuario> findById(Long id){
        return repository.findById(id);
    }

    public List<Usuario> findAll(){
       return repository.findAll();
    }
}
