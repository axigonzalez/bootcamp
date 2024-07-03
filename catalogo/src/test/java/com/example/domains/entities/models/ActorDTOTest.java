package com.example.domains.entities.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.domains.entities.Actor;

class ActorDTOTest {

	@Test
	void testFromActor() {
		Actor actor = new Actor(1, "John", "Doe");

		ActorDTO actorDTO = ActorDTO.from(actor);

		assertThat(actorDTO.getActorId()).isEqualTo(actor.getActorId());
		assertThat(actorDTO.getFirstName()).isEqualTo(actor.getFirstName());
		assertThat(actorDTO.getLastName()).isEqualTo(actor.getLastName());
	}

	@Test
	void testFromActorDTO() {
		ActorDTO actorDTO = new ActorDTO(1, "John", "Doe");

		Actor actor = ActorDTO.from(actorDTO);

		assertThat(actor.getActorId()).isEqualTo(actorDTO.getActorId());
		assertThat(actor.getFirstName()).isEqualTo(actorDTO.getFirstName());
		assertThat(actor.getLastName()).isEqualTo(actorDTO.getLastName());
	}

	@Test
	void testLombokGeneratedMethods() {
		ActorDTO actorDTO = new ActorDTO(1, "John", "Doe");

		assertThat(actorDTO.getActorId()).isEqualTo(1);
		assertThat(actorDTO.getFirstName()).isEqualTo("John");
		assertThat(actorDTO.getLastName()).isEqualTo("Doe");

		actorDTO.setActorId(2);
		actorDTO.setFirstName("Jane");
		actorDTO.setLastName("Smith");

		assertThat(actorDTO.getActorId()).isEqualTo(2);
		assertThat(actorDTO.getFirstName()).isEqualTo("Jane");
		assertThat(actorDTO.getLastName()).isEqualTo("Smith");
	}

	@Test
	void testToString() {
		ActorDTO actorDTO = new ActorDTO(1, "John", "Doe");

		assertThat(actorDTO.toString()).contains("ActorDTO", "actorId=1", "firstName=John", "lastName=Doe");
	}

	@Test
	void testEqualsAndHashCode() {
		
		ActorDTO actorDTO1 = new ActorDTO(1, "John", "Doe");
		ActorDTO actorDTO2 = new ActorDTO(1, "John", "Doe");
		ActorDTO actorDTO3 = new ActorDTO(2, "Jane", "Smith");

		assertThat(actorDTO1).isEqualTo(actorDTO2);
		assertThat(actorDTO1).isNotEqualTo(actorDTO3);
		assertThat(actorDTO1.hashCode()).isEqualTo(actorDTO2.hashCode());
		assertThat(actorDTO1.hashCode()).isNotEqualTo(actorDTO3.hashCode());
	}
}
