package com.agon.tcc.model;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import com.agon.tcc.dto.AmistosoDTO;
import com.agon.tcc.model.enums.Status;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "amistoso")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Amistoso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_horario", nullable = false)
	private LocalDateTime dataHorario;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = true)
    private Status statusAmistoso;
	
	@OneToOne(mappedBy = "amistoso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
    private SolicitacaoAmistoso solicitacaoAmistoso;
	
	@ManyToOne
	@JoinColumn(name = "modalidade_id", nullable = false)
	private Modalidade modalidade;
		
	public Amistoso(AmistosoDTO amistosoDTO) {
		this.id = amistosoDTO.id();
		try {
			this.dataHorario = amistosoDTO.dataHora();
		} catch(DateTimeException dte) {
			this.dataHorario = null;
		}
		this.statusAmistoso = amistosoDTO.statusAmistoso();
		this.modalidade = amistosoDTO.modalidade();
	}
	
}
