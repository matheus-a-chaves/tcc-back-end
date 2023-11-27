package com.agon.tcc.controllers;

import com.agon.tcc.model.entity.Equipe;
import com.agon.tcc.model.entity.Modalidade;
import com.agon.tcc.model.entity.Usuario;
import com.agon.tcc.service.EquipeService;
import com.agon.tcc.service.ModalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipe")
public class EquipeController {

    @Autowired
    private EquipeService service;

    @PostMapping("/create")
    public ResponseEntity<Equipe> create(@RequestBody Equipe equipe) {
        service.create(equipe);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<Equipe>> findAll(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body( service.findById(id));
    }

    @PostMapping("/findAll")
    public ResponseEntity<List<Equipe>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/findAllByIdUser")
    public ResponseEntity<List<Equipe>> findAllByIdUser(
            @RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllByIdUser(id));
    }


    @GetMapping("/findAllByIdModal")
    public ResponseEntity<List<Equipe>> findAllByIdModal(
            @RequestParam Long idUser, @RequestParam Long idModal) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllByIdModal(idUser, idModal));
    }

}
