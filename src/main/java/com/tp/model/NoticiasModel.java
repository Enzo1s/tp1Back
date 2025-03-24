package com.tp.model;

import java.util.Date;

import lombok.Data;

@Data
public class NoticiasModel {
	
	private Integer id;
	
	private String titulo;
	
	private String resumen;
	
	private String contenidoHTML;
	
	private char publicada;
	
	private Date fecha;
	
	private Integer empresa;
	
	private Date alta;
	
	private Date baja;
	
	private Date modificacion;
}
