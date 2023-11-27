package com.agon.tcc.controllers;

import com.agon.tcc.model.entity.Amistoso;
import com.agon.tcc.service.AmistosoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/amistoso")
public class AmistosoController {
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

    @GetMapping("/findAllIdUser")
    public ResponseEntity<List<Amistoso>> findAllIdUser(
            @RequestParam Long id, @RequestParam String status, @RequestParam Long idModalidade
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllIdUser(id, status, idModalidade));
    }
    @GetMapping("/findAllNotifications")
    public ResponseEntity<List<Amistoso>> findAllNotifications(
            @RequestParam Long id, @RequestParam String status
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllNotifications(id, status));
    }

    @PutMapping("/status")
    public ResponseEntity updateAmistoso(
            @RequestParam Long id, @RequestParam String status
    ) {
        service.updateAmistoso(id, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}