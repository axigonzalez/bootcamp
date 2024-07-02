package com.example.domains.entities.models;

import java.io.Serializable;
import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilmDTOComplete implements Serializable {
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("titulo")
	private String title;
	@JsonProperty("idioma")
	private int languageId;
	@JsonProperty("lanzamiento")
	private int releaseYear;
	@JsonProperty("duracion")
	private int rentalDuration;
//	private List<Integer> actorIds;
//
//	private List<Integer> categoryIds;

	public static FilmDTOComplete from(Film source) {
		return new FilmDTOComplete(source.getFilmId(), source.getTitle(), source.getLanguage().getLanguageId(), source.getReleaseYear(),
				source.getRentalDuration());
	}

//	public static Film from(FilmDTOComplete source) {
//		return new Film(source.getFilmId(), source.getTitle(), source.getLanguageId(), source.getReleaseYear(),
//				source.getRentalDuration());
//	}
}
