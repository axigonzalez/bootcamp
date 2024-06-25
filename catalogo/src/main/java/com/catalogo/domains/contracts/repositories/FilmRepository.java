package com.catalogo.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.catalogo.domains.core.contracts.repositories.RepositoryWithProjections;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.ActorShort;

public interface FilmRepository  extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film>, 
RepositoryWithProjections {

	List<Film> findTop5ByLastNameStartingWithOrderByFirstNameDesc(String prefijo);
	List<Film> findTop5ByLastNameStartingWith(String prefijo, Sort orderBy);
	
	List<Film> findByActorIdGreaterThanEqual(int filmId);
	@Query(value = "from Film f where f.filmId >= ?1")
	List<Film> findByJPQL(int filmId);
	@Query(value = "SELECT * FROM film WHERE film_id >= ?1", nativeQuery = true)
	List<Film> findBySQL(int id);
	
	List<ActorDTO> readByActorIdGreaterThanEqual(int actorId);
	List<ActorShort> queryByActorIdGreaterThanEqual(int actorId);
	
	<T> List<T> findByActorIdGreaterThanEqual(int actorId, Class<T> proyeccion);
}
