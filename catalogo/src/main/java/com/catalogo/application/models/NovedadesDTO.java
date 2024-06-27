package com.catalogo.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.Language;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.FilmDTO;

@Data @AllArgsConstructor @NoArgsConstructor
public class NovedadesDTO {
	private List<FilmDTO> films;
	private List<ActorDTO> actors;
	private List<Category> categories;
	private List<Language> languages;
	
}