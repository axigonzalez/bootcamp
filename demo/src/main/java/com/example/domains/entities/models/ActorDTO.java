package com.example.domains.entities.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ActorDTO {
	private int actorId;
	private String firstName;
	private String lastName;
}
