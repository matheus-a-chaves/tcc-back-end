package com.agon.tcc.service;

import com.agon.tcc.model.entity.Amistoso;
import com.agon.tcc.model.entity.Equipe;
import com.agon.tcc.model.entity.Usuario;
import com.agon.tcc.model.persistence.AmistosoRepository;
import com.agon.tcc.model.persistence.EquipeRepository;
import com.agon.tcc.model.persistence.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AmistosoService {

    private final AmistosoRepository repository;

    private final EquipeRepository equipeRepository;
    private Logger logger = Logger.getLogger(AmistosoService.class.getName());

    public Amistoso create(Amistoso amistoso){
        return  repository.save(amistoso);
    }
    public List<Amistoso> findAll(){
        return repository.findAll();
    }
    public List<Amistoso> findAllIdUser(Long id, String status, Long idModalidade){
        List<Equipe> equipe = equipeRepository.findAllByIdUser(id);
        List<Long> idsDasEquipes = equipe.stream()
                .map(Equipe::getId)
                .collect(Collectors.toList());

        return repository.findAllIdUser(idsDasEquipes, status, idModalidade);
    }
    public List<Amistoso> findAllNotifications(Long id, String status){
        List<Equipe> equipe = equipeRepository.findAllByIdUser(id);
        List<Long> idsDasEquipes = equipe.stream()
                .map(Equipe::getId)
                .collect(Collectors.toList());
         return repository.findAllNotifications(idsDasEquipes, status);
    }
    @Transactional
    public void updateAmistoso(Long id, String status){
        repository.updateAmistoso(id, status);
    }
}
