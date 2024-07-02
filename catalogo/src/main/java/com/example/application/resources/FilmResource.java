package com.example.application.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.resources.ActorResource.Peli;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.domains.entities.models.CategoryDTO;
import com.example.domains.entities.models.FilmDTO;
import com.example.domains.entities.models.FilmShortDTO;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/peliculas/v1")
public class FilmResource {

	private FilmService srv;

	public FilmResource(FilmService srv) {
		this.srv = srv;
	}

	@GetMapping
	public List getAll() {
		return srv.getAll();
	}
	
	@GetMapping(path = "/{id}")
	public FilmDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return FilmDTO.from(item.get());
	}
	
	record ActorRecord(int id, String nombre, String apellidos) {
	}

	@GetMapping(path = "/{id}/actores")
	@Transactional
	public List<ActorRecord> getActors(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmActors().stream()
				.map(o -> new ActorRecord(o.getActor().getActorId(), o.getActor().getFirstName(), o.getActor().getLastName()))
				.toList();
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}
}
