package com.agon.tcc.controllers;

import com.agon.tcc.model.entity.Modalidade;
import com.agon.tcc.model.entity.Usuario;
import com.agon.tcc.service.UsuarioService;
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
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping("/create")
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        service.create(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<Usuario>> findAll(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body( service.findById(id));
    }

    @PostMapping("/findAll")
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body( service.findAll());
    }
}
