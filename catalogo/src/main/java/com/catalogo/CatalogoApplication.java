package com.catalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.catalogo.domains.contracts.services.CategoryService;
import com.catalogo.domains.entities.models.CategoryDTO;


@SpringBootApplication
public class CatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@Autowired
	CategoryService srv;
	
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
		srv.getByProjection(CategoryDTO.class).forEach(System.out::println);
		//probando git
	}
	
}
