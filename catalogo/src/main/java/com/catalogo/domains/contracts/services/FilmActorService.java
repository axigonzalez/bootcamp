package com.catalogo.domains.contracts.services;

import com.catalogo.domains.core.contracts.services.ProjectionDomainService;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.FilmActor;
import com.catalogo.domains.entities.FilmActorPK;

public interface FilmActorService extends ProjectionDomainService<FilmActor, FilmActorPK>{

}