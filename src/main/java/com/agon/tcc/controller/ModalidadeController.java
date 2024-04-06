package com.agon.tcc.controller;

import com.agon.tcc.dto.ModalidadeDTO;
import com.agon.tcc.service.ModalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/agon/modalidades")
public class ModalidadeController {
    @Autowired
    private ModalidadeService modalidadeService;

    @GetMapping
    public ResponseEntity<List<ModalidadeDTO>> findAll() {
        List<ModalidadeDTO> modalidadeDTO = this.modalidadeService.findAll();
        return ResponseEntity.ok().body(modalidadeDTO);
    }
}
