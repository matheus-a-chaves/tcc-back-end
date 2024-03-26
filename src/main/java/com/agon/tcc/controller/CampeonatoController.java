package com.agon.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agon.tcc.service.CampeonatoService;

@RestController
@RequestMapping("/agon/campeonatos")
public class CampeonatoController {
	
	@Autowired
	private CampeonatoService campeonatoService;
		
}
