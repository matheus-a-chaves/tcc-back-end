package com.agon.tcc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agon.tcc.model.Campeonato;
import com.agon.tcc.model.Formato;
import com.agon.tcc.model.Modalidade;

@SpringBootApplication
public class TccApplication {

	public static void main(String[] args) {
		
		// Instanciando e populando a lista estática de Campeonato
        List<Campeonato> listCampeonato = new ArrayList<>();
        
        // Adicionando alguns campeonatos à lista
        listCampeonato.add(
            Campeonato.builder()
                .id(1L)
                .nome("Campeonato de Futebol")
                .quantidadeEquipes(10)
                .dataInicio(LocalDate.of(2024, 4, 1))
                .dataFim(LocalDate.of(2024, 6, 30))
                .regulamento(new byte[]{})
                .imagemCampeonato(new byte[]{})
                .formato(Formato.ELIMINATORIA_SIMPLES)
                .modalidade(Modalidade.FUTEBOL)
                .build()
        );

        listCampeonato.add(
            Campeonato.builder()
                .id(2L)
                .nome("Campeonato de Basquete")
                .quantidadeEquipes(8)
                .dataInicio(LocalDate.of(2024, 5, 1))
                .dataFim(LocalDate.of(2024, 7, 31))
                .regulamento(new byte[]{})
                .imagemCampeonato(new byte[]{})
                .formato(Formato.PONTOS_CORRIDOS)
                .modalidade(Modalidade.BASQUETE)
                .build()
        );
        
        listCampeonato.add(
                Campeonato.builder()
                    .id(3L)
                    .nome("Campeonato de Basquete")
                    .quantidadeEquipes(12)
                    .dataInicio(LocalDate.of(2024, 6, 1))
                    .dataFim(LocalDate.of(2024, 8, 31))
                    .regulamento(new byte[]{})
                    .imagemCampeonato(new byte[]{})
                    .formato(Formato.PONTOS_CORRIDOS)
                    .modalidade(Modalidade.BASQUETE)
                    .build()
            );
		
		SpringApplication.run(TccApplication.class, args);
		
		
	}

}
