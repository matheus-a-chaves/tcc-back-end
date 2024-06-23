package com.agon.tcc.model;

import java.time.LocalDateTime;

import com.agon.tcc.dto.SolicitacaoAmistosoDTO;
import com.agon.tcc.model.enums.StatusSolicitacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "solicitacao_amistoso")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SolicitacaoAmistoso {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao;

    @OneToOne
    @JoinColumn(name = "amistoso_id", nullable = false)
    private Amistoso amistoso;

    @ManyToOne
    @JoinColumn(name = "equipe_visitante_id", nullable = false)
    private Equipe equipeVisitante;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSolicitacao status;
    
    public SolicitacaoAmistoso(SolicitacaoAmistosoDTO solicitacaoAmistosoDTO) {
    	this.id = solicitacaoAmistosoDTO.id();
    	this.dataSolicitacao = solicitacaoAmistosoDTO.dataSolicitacao();
    	this.amistoso = solicitacaoAmistosoDTO.amistoso();
    	this.equipeVisitante = solicitacaoAmistosoDTO.equipeVisitante();
    }

}
