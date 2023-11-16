package com.agon.tcc.controllers;

import com.agon.tcc.model.entity.Modalidade;
import com.agon.tcc.service.ModalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/modalidade")
public class ModalidadeController {
    @Autowired
    private ModalidadeService service;

    @PostMapping("/findAll")
    public ResponseEntity<List<Modalidade>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
}
