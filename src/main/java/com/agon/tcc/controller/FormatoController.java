package com.agon.tcc.controller;

import com.agon.tcc.dto.FormatoDTO;
import com.agon.tcc.dto.ModalidadeDTO;
import com.agon.tcc.service.FormatoService;
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
@RequestMapping("/agon/formatos")
public class FormatoController {
    @Autowired
    private FormatoService formatoService;

    @GetMapping
    public ResponseEntity<List<FormatoDTO>> findAll() {
        List<FormatoDTO> formatoDTO = this.formatoService.findAll();
        return ResponseEntity.ok().body(formatoDTO);
    }
}
