package com.catalogo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.catalogo.domains.contracts.services.ActorService;
import com.catalogo.domains.contracts.services.CategoryService;
import com.catalogo.domains.contracts.services.FilmService;
import com.catalogo.domains.contracts.services.LanguageService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.Language;
import com.catalogo.domains.entities.models.CategoryDTO;
import com.catalogo.domains.entities.models.LanguageDTO;

import jakarta.transaction.Transactional;


@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@Autowired
	CategoryService srv;
	
	@Autowired
	LanguageService ls;
	
	@Autowired
	FilmService fs;
	
	@Autowired
	ActorService as;
	
	@Transactional
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
		srv.getByProjection(CategoryDTO.class).forEach(System.out::println);
//		ls.add(new Language(7, "Español"));
		ls.getByProjection(LanguageDTO.class).forEach(System.out::println);
		System.out.println(fs.getOne(3));
//		
//
//	       Actor actor = new Actor(15);
//	       Film pelicula = new Film(3);
//	        fs.save(pelicula);
//	        System.out.println(fs.getOne(3));
//		
		

	}
	
}
