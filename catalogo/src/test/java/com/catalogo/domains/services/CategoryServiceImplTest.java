package com.catalogo.domains.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.catalogo.domains.contracts.repositories.CategoryRepository;
import com.catalogo.domains.contracts.services.CategoryService;
import com.catalogo.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;


public class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository dao;

    private CategoryService srv;

    @BeforeEach
    void setUp() {
        srv = new CategoryServiceImpl(dao);
    }

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
    void testAdd_valid() throws DuplicateKeyException, InvalidDataException, com.catalogo.exceptions.DuplicateKeyException, com.catalogo.exceptions.InvalidDataException {
        Category categoryToAdd = new Category(1, "New Category");

        when(dao.save(any(Category.class))).thenReturn(categoryToAdd);

        Category result = srv.add(categoryToAdd);

        assertThat(result).isEqualTo(categoryToAdd);
        verify(dao, times(1)).save(categoryToAdd);
    }

    @Test
    void testAdd_invalidData() {
        Category invalidCategory = new Category(); 

        assertThrows(InvalidDataException.class, () -> srv.add(invalidCategory));

        verify(dao, never()).save(any());
    }

    @Test
    void testDelete_valid() throws InvalidDataException, com.catalogo.exceptions.InvalidDataException {
        Category categoryToDelete = new Category(1, "Category to Delete");

        srv.delete(categoryToDelete);

        verify(dao, times(1)).delete(categoryToDelete);
    }

 

}
