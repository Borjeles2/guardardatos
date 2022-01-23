package com.proyecto.scraping.Beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "Pelicula")
@Table(name = "datospeliculas", schema = "public")
@Data
public class Pelicula {

//	@Id // Marca el campo como la clave de la tabla
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;

	@Id
	private String nombre;

	@Column(name = "fechaestreno")
	private Date fechaestreno;

	@Column(name = "imagen")
	private String imagen;

//	@Column(name = "imagen", columnDefinition = "bytea")
//	private byte[] imagen;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaestreno() {
		return fechaestreno;
	}

	public void setFechaestreno(Date fechaestreno) {
		this.fechaestreno = fechaestreno;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
