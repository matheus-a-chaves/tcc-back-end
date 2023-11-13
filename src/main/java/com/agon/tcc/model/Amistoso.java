package com.agon.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Amistoso {
    private String data;
    private String horario;
    private String logo;
    private String status;
    private String modalidade;
    private String local;
    private String timeConvidado;
}
