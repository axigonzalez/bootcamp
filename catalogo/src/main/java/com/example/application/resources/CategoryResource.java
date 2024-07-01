package com.example.application.resources;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;

@RestController
@RequestMapping("/api/categorias/v1")
public class CategoryResource {

	private CategoryService srv;

	public CategoryResource(CategoryService srv) {
		this.srv = srv;
	}
	
	@GetMapping
	public List getAll;
}
