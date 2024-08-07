package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>{

	List<Actor> findTop5ByLastNameStartingWithOrderByFirstNameDesc(String prefijo);
	List<Actor> findTop5ByLastNameStartingWith(String prefijo, Sort orderBy);
	
	List<Actor> findByActorIdGreaterThanEqual(int actorId);
	
	@Query(value = "from Actor a where a.actorId >= ?1")
	List<Actor> findByJPQL(int actorId);
	
	@Query(value = "select * from actor where actor_id >= ?1", nativeQuery = true)
	List<Actor> findBySQL(int id);
 }
