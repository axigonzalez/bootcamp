package com.catalogo.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.catalogo.domains.core.contracts.repositories.RepositoryWithProjections;
import com.catalogo.domains.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category>, 
RepositoryWithProjections {

	List<Category> findByCategoryIdGreaterThanEqual(int categoryId);
	@Query(value = "from Category c where c.categoryId >= ?1")
	List<Category> findByJPQL(int categoryId);
	@Query(value = "SELECT * FROM category WHERE category_id >= ?1", nativeQuery = true)
	List<Category> findBySQL(int id);
	

	<T> List<T> findByCategoryIdGreaterThanEqual(int categoryId, Class<T> proyeccion);
	
}
