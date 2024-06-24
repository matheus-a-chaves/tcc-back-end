package com.agon.tcc.controller;

import com.agon.tcc.dto.MembroDTO;
import com.agon.tcc.service.MembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/agon/membros")
public class MembroController {
    @Autowired
    MembroService membroService;

    @GetMapping("/jogador/{id}")
    public ResponseEntity<MembroDTO> findByIdAtletica(@PathVariable Long id) {
        MembroDTO membroDTO = this.membroService.findMembroByIdJogador(id);
        return ResponseEntity.ok().body(membroDTO);
    }

}
