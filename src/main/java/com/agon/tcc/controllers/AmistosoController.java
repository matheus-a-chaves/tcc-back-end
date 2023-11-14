package com.agon.tcc.controllers;

import com.agon.tcc.model.entity.Amistoso;
import com.agon.tcc.service.AmistosoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/amistoso")
public class AmistosoController{
    @Autowired
    private AmistosoService service;

    @PostMapping("/create")
    public ResponseEntity<Amistoso> create(@RequestBody Amistoso amistoso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(amistoso));
    }
    @PostMapping("/findAll")
    public ResponseEntity<List<Amistoso>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
}