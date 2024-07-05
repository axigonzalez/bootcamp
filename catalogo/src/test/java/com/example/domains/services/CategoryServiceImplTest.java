package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

class CategoryServiceImplTest {

	@Mock
	private CategoryRepository dao;

	@InjectMocks
	private CategoryServiceImpl srv;

	private AutoCloseable closeable;

	@BeforeEach
	void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	void testGetAll() {
		List<Category> categories = Arrays.asList(new Category(1, "Terror"), new Category(2, "Accion"));
		when(dao.findAll()).thenReturn(categories);
		List<Category> result = srv.getAll();
		assertEquals(2, result.size());
		verify(dao).findAll();
	}

	@Test
	void testGetOne() {
		Category category = new Category(1, "Terror");
		when(dao.findById(1)).thenReturn(Optional.of(category));
		Optional<Category> result = srv.getOne(1);
		assertTrue(result.isPresent());
		assertEquals("Terror", result.get().getName());
		verify(dao).findById(1);
	}

	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		Category category = new Category(1, "Terror");
		when(dao.existsById(1)).thenReturn(false);
		when(dao.save(category)).thenReturn(category);
		Category result = srv.add(category);
		assertEquals(category, result);
		verify(dao).save(category);
	}

	@Test
	void testModify() throws NotFoundException, InvalidDataException {
		Category category = new Category(1, "Terror");
		when(dao.existsById(1)).thenReturn(true);
		when(dao.save(category)).thenReturn(category);
		Category result = srv.modify(category);
		assertEquals(category, result);
		verify(dao).save(category);
	}

	@Test
	void testDelete() throws InvalidDataException {
		Category category = new Category(1, "Terror");
		srv.delete(category);
		verify(dao).delete(category);
	}

	@Test
	void testDeleteById() {
		srv.deleteById(1);
		verify(dao).deleteById(1);
	}

	@Test
	void testNovedades() {
		List<Category> categories = Arrays.asList(new Category(1, "Terror"), new Category(2, "Accion"));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		when(dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(timestamp)).thenReturn(categories);
		List<Category> result = srv.novedades(timestamp);
		assertEquals(2, result.size());
		verify(dao).findByLastUpdateGreaterThanEqualOrderByLastUpdate(timestamp);
	}

	@Test
	void testAddNullCategory() {
		assertThrows(InvalidDataException.class, () -> {
			srv.add(null);
		});
	}

	@Test
	void testAddInvalidCategory() {
		Category category = new Category(0, "");
		assertThrows(InvalidDataException.class, () -> {
			srv.add(category);
		});
	}

	@Test
	void testAddDuplicateCategory() {
		Category category = new Category(1, "Terror");
		when(dao.existsById(1)).thenReturn(true);
		assertThrows(DuplicateKeyException.class, () -> {
			srv.add(category);
		});
	}

	@Test
	void testModifyNullCategory() {
		assertThrows(InvalidDataException.class, () -> {
			srv.modify(null);
		});
	}

	@Test
	void testModifyInvalidCategory() {
		Category category = new Category(0, "");
		assertThrows(InvalidDataException.class, () -> {
			srv.modify(category);
		});
	}

	@Test
	void testModifyNotFoundCategory() {
		Category category = new Category(1, "Terror");
		when(dao.existsById(1)).thenReturn(false);
		assertThrows(NotFoundException.class, () -> {
			srv.modify(category);
		});
	}

	@Test
	void testDeleteNullCategory() {
		assertThrows(InvalidDataException.class, () -> {
			srv.delete(null);
		});
	}
}
