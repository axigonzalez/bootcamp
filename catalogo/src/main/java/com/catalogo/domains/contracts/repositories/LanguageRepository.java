package com.catalogo.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.catalogo.domains.core.contracts.repositories.RepositoryWithProjections;
import com.catalogo.domains.entities.Language;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.ActorShort;


public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language>, 
RepositoryWithProjections {
	List<Language> findTop5ByLastNameStartingWithOrderByFirstNameDesc(String prefijo);
	List<Language> findTop5ByLastNameStartingWith(String prefijo, Sort orderBy);
	
	List<Language> findByLanguageIdGreaterThanEqual(int languageId);
	@Query(value = "from Language a where a.actorId >= ?1")
	List<Language> findByJPQL(int actorId);
	@Query(value = "SELECT * FROM language WHERE language_id >= ?1", nativeQuery = true)
	List<Language> findBySQL(int id);
	
	List<ActorDTO> readByLanguageIdGreaterThanEqual(int languageId);
	List<ActorShort> queryByLanguageIdGreaterThanEqual(int languageId);
	
	<T> List<T> findByLanguageIdGreaterThanEqual(int languageId, Class<T> proyeccion);
	
}
