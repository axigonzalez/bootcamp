package com.catalogo.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.catalogo.domains.contracts.repositories.CategoryRepository;
import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.models.CategoryDTO;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.catalogo.domains.contracts.services.CategoryService;


public class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository dao;

    private CategoryService srv = new CategoryServiceImpl(dao);

    @Test
    void testGetAll() {
        List<Category> categories = Arrays.asList(
                new Category(1, "Category 1"),
                new Category(2, "Category 2"),
                new Category(3, "Category 3"));

        when(dao.findAll()).thenReturn(categories);

        List<Category> result = srv.getAll();

        assertThat(result.size()).isEqualTo(3);
        verify(dao, times(1)).findAll();
    }

    @Test
    void testGetOne_valid() {
        when(dao.findById(1)).thenReturn(Optional.of(new Category(1, "Category 1")));

        Optional<Category> result = srv.getOne(1);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void testGetOne_notFound() {
        when(dao.findById(1)).thenReturn(Optional.empty());

        Optional<Category> result = srv.getOne(1);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void testAdd_valid() throws DuplicateKeyException, InvalidDataException {
        Category category = new Category(1, "New Category");

        when(dao.save(any(Category.class))).thenReturn(category);

        Category result = null;
		try {
			result = srv.add(category);
		} catch (com.catalogo.exceptions.DuplicateKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (com.catalogo.exceptions.InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        assertThat(result).isEqualTo(category);
        verify(dao, times(1)).save(category);
    }

    @Test
    void testAdd_invalidData() {
        Category invalidCategory = new Category(); // No se establecen datos válidos

        assertThrows(InvalidDataException.class, () -> srv.add(invalidCategory));

        verify(dao, never()).save(any());
    }

    @Test
    void testAdd_duplicateKey() {
        Category existingCategory = new Category(1, "Existing Category");

        when(dao.existsById(existingCategory.getCategoryId())).thenReturn(true);

        assertThrows(DuplicateKeyException.class, () -> srv.add(existingCategory));

        verify(dao, never()).save(any());
    }

    @Test
    void testModify_valid() throws NotFoundException, InvalidDataException, com.catalogo.exceptions.NotFoundException, com.catalogo.exceptions.InvalidDataException {
        Category categoryToUpdate = new Category(1, "Updated Category");

        when(dao.existsById(categoryToUpdate.getCategoryId())).thenReturn(true);
        when(dao.save(any(Category.class))).thenReturn(categoryToUpdate);

        Category result = srv.modify(categoryToUpdate);

        assertThat(result).isEqualTo(categoryToUpdate);
        verify(dao, times(1)).save(categoryToUpdate);
    }

    @Test
    void testModify_notFound() {
        Category categoryToUpdate = new Category(1, "Updated Category");

        when(dao.existsById(categoryToUpdate.getCategoryId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> srv.modify(categoryToUpdate));

        verify(dao, never()).save(any());
    }

    @Test
    void testDelete_valid() throws InvalidDataException, com.catalogo.exceptions.InvalidDataException {
        Category categoryToDelete = new Category(1, "Category to Delete");

        srv.delete(categoryToDelete);

        verify(dao, times(1)).delete(categoryToDelete);
    }

    @Test
    void testDelete_invalidData() {
        Category invalidCategory = new Category(); // No se establecen datos válidos

        assertThrows(InvalidDataException.class, () -> srv.delete(invalidCategory));

        // Verifica que el método delete (sin argumentos) nunca fue invocado
        verify(dao, never()).deleteAll();
    }


    @Test
    void testDeleteById() {
        Integer categoryId = 1;

        srv.deleteById(categoryId);

        verify(dao, times(1)).deleteById(categoryId);
    }

    @Test
    void testGetAllByProjection() {
        List<CategoryDTO> projectedCategories = Arrays.asList(
                new CategoryDTO(1, "Category 1"),
                new CategoryDTO(2, "Category 2"));

        when(dao.findAllBy(CategoryDTO.class)).thenReturn(projectedCategories);

        List<CategoryDTO> result = srv.getByProjection(CategoryDTO.class);

        assertThat(result.size()).isEqualTo(2);
        verify(dao, times(1)).findAllBy(CategoryDTO.class);
    }


}

