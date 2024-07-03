package com.example.domains.entities.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class FilmDTO implements Serializable {
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("titulo")
	private String title;
	@JsonProperty("año")
	private Short releaseYear;
	@JsonProperty("duracion")
	private byte rentalDuration;
	@JsonProperty("puntuacion")
	private BigDecimal rentalRate;
	@JsonProperty("costes")
	private BigDecimal replacementCost;
	@JsonProperty("idioma")
	private int languageId;

	public static FilmDTO from(Film film) {
		return new FilmDTO(film.getFilmId(), film.getTitle(), film.getReleaseYear(), film.getRentalDuration(), film.getRentalRate(),
				film.getReplacementCost(), film.getLanguage());
	}

	public static Film from(FilmDTO film) {
		return new Film(film.getFilmId(), film.getTitle(), film.getReleaseYear(), film.getRentalDuration(), film.getRentalRate(),
				film.getReplacementCost(), new Language(film.getLanguageId()));
	}


	public FilmDTO(int filmId, String title, Short releaseYear, byte rentalDuration, BigDecimal rentalRate,
			BigDecimal replacementCost, Language language) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.releaseYear = releaseYear;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
		this.languageId = language.getLanguageId();
	}
	
	
	
}
