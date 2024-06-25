package com.catalogo.domains.entities.models;

import java.io.Serializable;

import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FilmDTO implements Serializable{
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("descripcion")
	private String description;
	@JsonProperty("año")
	private String releaseYear;
	@JsonProperty("titulo")
	private String title;
	
	public static FilmDTO from(Film source) {
		return new FilmDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getReleaseYear(),
				source.getTitle());
	}
	
	public static Film from(FilmDTO source) {
		return new Actor(
				source.getFilmId(), 
				source.getDescription(),
				source.getReleaseYear(),
				source.getTitle());

				
	}

}
