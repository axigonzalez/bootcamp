package com.example.domains.entities.models;

import java.io.Serializable;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class FilmDTO implements Serializable {
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("titulo")
	private String title;
	@JsonProperty("descripcion")
	private String description;

	public static FilmDTO from(Film source) {
		return new FilmDTO(
				source.getFilmId(), 
				source.getTitle(), 
				source.getDescription()
				);
	}
	public static Film from(FilmDTO source) {
		return new Film(
				source.getFilmId(), 
				source.getTitle(), 
				source.getDescription()
				);
	}
}
