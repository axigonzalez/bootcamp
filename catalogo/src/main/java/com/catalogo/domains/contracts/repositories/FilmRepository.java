package com.catalogo.domains.contracts.repositories;

import java.security.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.catalogo.domains.core.contracts.repositories.RepositoryWithProjections;
import com.catalogo.domains.entities.Film;

public interface FilmRepository  extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film>, 
RepositoryWithProjections {

	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);

	List<Film> findByJPQL(int filmId);

	Object findBySQL(int id);
	
}
