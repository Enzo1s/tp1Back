package com.tp.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Empresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8749697010735207518L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128)
	private String denominacion;
	
	@Column(length = 50)
	private String telefono;
	
	@Column(length = 256)
	private String horarioAtencion;
	
	@Column(length = 1024)
	private String quienesSomos;
	
	private Double longitud;
	
	private Double latitud;
	
	@Column(length = 256)
	private String domicilio;
	
	@Column(length = 75)
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date baja;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacion;
}
