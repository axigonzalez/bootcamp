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

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.domains.entities.models.CategoryDTO;
import com.example.domains.entities.models.LanguageDTO;
import com.example.exceptions.NotFoundException;

@RestController
@RequestMapping("/api/idiomas/v1")
public class LanguageResource {

	private LanguageService srv;

	public LanguageResource(LanguageService srv) {
		this.srv = srv;
	}

	@GetMapping
	public List getAll() {
		return srv.getAll();
	}
	
	@GetMapping(path = "/{id}")
	public LanguageDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return LanguageDTO.from(item.get());
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}
