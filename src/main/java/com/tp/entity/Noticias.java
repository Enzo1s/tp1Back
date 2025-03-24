package com.tp.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Noticias implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2664938646218715867L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128)
	private String titulo;
	
	@Column(length = 1024)
	private String resumen;
	
	@Column(length = 128)
	private String imagen;
	
	@Column(columnDefinition = "LONGTEXT")
	private String contenidoHTML;
	
	@Column(length = 128)
	private char publicada;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date baja;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacion;
}
