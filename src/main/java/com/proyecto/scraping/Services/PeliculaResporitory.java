package com.proyecto.scraping.Services;


import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.scraping.Beans.Pelicula;


public interface PeliculaResporitory extends JpaRepository<Pelicula, Long>{
	
	
}
