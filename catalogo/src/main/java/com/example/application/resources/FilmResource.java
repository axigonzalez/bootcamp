package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.application.resources.ActorResource.Peli;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.domains.entities.models.CategoryDTO;
import com.example.domains.entities.models.FilmDTO;
import com.example.domains.entities.models.FilmDTOComplete;
import com.example.domains.entities.models.FilmShortDTO;
import com.example.domains.entities.models.LanguageDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/peliculas/v1")
public class FilmResource {

	private FilmService srv;

	public FilmResource(FilmService srv) {
		this.srv = srv;
	}

	@GetMapping
	public List getAll() {
		return srv.getByProjection(FilmDTO.class);
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
	
//	@PostMapping
//	public ResponseEntity<Object> create(@Valid @RequestBody FilmDTOComplete item)
//			throws BadRequestException, DuplicateKeyException, InvalidDataException {
//		var newItem = srv.add(FilmDTOComplete.from(item));
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//				.buildAndExpand(newItem.getFilmId()).toUri();
//		return ResponseEntity.created(location).build();
//	}
	
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getFilmId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(FilmDTO.from(item));
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}
}
