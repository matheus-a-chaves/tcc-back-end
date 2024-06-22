package com.agon.tcc.model;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import com.agon.tcc.dto.AmistosoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@Column(name = "status", nullable = false)
	private String status;
	
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
		this.status = amistosoDTO.status();
		this.modalidade = amistosoDTO.modalidade();
	}
	
}
