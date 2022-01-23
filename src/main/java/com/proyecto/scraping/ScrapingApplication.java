package com.proyecto.scraping;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.proyecto.scraping.Services.PeliculaServiceImpl;

@SpringBootApplication

public class ScrapingApplication {

	@Autowired
	static PeliculaServiceImpl peliculaServiceImpl;

	public static void main(String[] args) throws ParseException {
//		SpringApplication.run(ScrapingApplication.class, args);
		ApplicationContext applicationContext = SpringApplication.run(ScrapingApplication.class, args);
		PeliculaServiceImpl peliculaServiceImpl = applicationContext.getBean(PeliculaServiceImpl.class);
		String url = applicationContext.getEnvironment().getProperty("spring.datasource.URL");
		peliculaServiceImpl.savePelicula(url);

	}

}
