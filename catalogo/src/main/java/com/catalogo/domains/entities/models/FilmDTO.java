package com.catalogo.domains.entities.models;

import java.io.Serializable;

import com.catalogo.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FilmDTO implements Serializable{
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("nombre")
	private String firstName;
	@JsonProperty("apellidos")
	private String lastName;
	
	public static ActorDTO from(Actor source) {
		return new ActorDTO(
				source.getActorId(), 
				source.getFirstName(),
				source.getLastName());
	}
	
	public static Actor from(ActorDTO source) {
		return new Actor(
				source.getActorId(),
				source.getFirstName(),
				source.getLastName());
				
	}{

}
