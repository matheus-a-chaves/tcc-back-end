package com.agon.tcc.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartidasResponseDTO {

    private Long idPartida;
    private String nomeEtapa;
    private Long campeonato;
    private Long totalRodadas;
    private Equipe equipeUm;
    private Equipe equipeDois;
    private Endereco endereco;
    private LocalDateTime dataPartida;
    private boolean partidaFinalizada;
    private boolean partidaExpirada;

}
