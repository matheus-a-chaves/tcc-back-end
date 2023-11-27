package com.agon.tcc.controllers;

import com.agon.tcc.model.entity.Equipe;
import com.agon.tcc.model.entity.Local;
import com.agon.tcc.service.EquipeService;
import com.agon.tcc.service.LocalService;
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
@RequestMapping("/local")
public class LocalController {
    @Autowired
    private LocalService service;

    @PostMapping("/create")
    public ResponseEntity<Local> create(@RequestBody Local local) {
        service.create(local);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<Local>> findById(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body( service.findById(id));
    }

    @PostMapping("/findAll")
    public ResponseEntity<List<Local>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
}
