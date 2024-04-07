package com.agon.tcc.service;

import com.agon.tcc.dto.FormatoDTO;
import com.agon.tcc.dto.ModalidadeDTO;
import com.agon.tcc.model.Formato;
import com.agon.tcc.model.Modalidade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormatoService {
    public List<FormatoDTO> findAll() {
        List<FormatoDTO> formatos = new ArrayList<>();

        for (Formato formato : Formato.values()) {
            FormatoDTO formatoDTO = new FormatoDTO(
                    formato.getCodigoFormato(),
                    formato.getNomeFormato());
            formatos.add(formatoDTO);
        }
        return formatos;
    }
}
