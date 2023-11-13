package com.agon.tcc.service;

import com.agon.tcc.model.Amistoso;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AmistosoService {

    private Logger logger = Logger.getLogger(AmistosoService.class.getName());

    public Amistoso create(Amistoso amistoso) {
        logger.info("Creating one amistoso");
        return amistoso;
    }
}
