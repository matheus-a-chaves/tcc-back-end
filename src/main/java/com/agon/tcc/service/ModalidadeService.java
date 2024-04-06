package com.agon.tcc.service;

import com.agon.tcc.dto.ModalidadeDTO;
import com.agon.tcc.model.Modalidade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModalidadeService {
    public List<ModalidadeDTO> findAll() {
        List<ModalidadeDTO> modalidades = new ArrayList<>();

        for (Modalidade modalidade : Modalidade.values()) {
            ModalidadeDTO modalidadeDTO = new ModalidadeDTO(
                    modalidade.getCodigoModalidade(),
                    modalidade.name());
            modalidades.add(modalidadeDTO);
        }

        return modalidades;
    }
}
