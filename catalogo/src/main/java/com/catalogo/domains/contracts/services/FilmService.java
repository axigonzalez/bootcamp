package com.catalogo.domains.contracts.services;

import java.security.Timestamp;
import java.util.List;

import com.catalogo.domains.core.contracts.services.ProjectionDomainService;
import com.catalogo.domains.entities.Film;

public interface FilmService extends ProjectionDomainService<Film, Integer>{

	void save(Film film);
	List<Film> novedades(Timestamp fecha);
}