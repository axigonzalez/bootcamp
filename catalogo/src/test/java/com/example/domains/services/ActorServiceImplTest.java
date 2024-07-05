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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

class ActorServiceImplTest {

	@Mock
	private ActorRepository dao;

	@InjectMocks
	private ActorServiceImpl srv;

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
	void testGetByProjectionClass() {
		List<Actor> actors = Arrays.asList(new Actor(1, "Pepito", "Grillo"), new Actor(2, "Carmelo", "Coton"));
		when(dao.findAllBy(Actor.class)).thenReturn(actors);
		List<Actor> result = srv.getByProjection(Actor.class);
		assertEquals(2, result.size());
		verify(dao).findAllBy(Actor.class);
	}

	@Test
	void testGetByProjectionSortClass() {
		List<Actor> actors = Arrays.asList(new Actor(1, "Pepito", "Grillo"), new Actor(2, "Carmelo", "Coton"));
		when(dao.findAllBy(Sort.by("id"), Actor.class)).thenReturn(actors);
		Iterable<Actor> result = srv.getByProjection(Sort.by("id"), Actor.class);
		assertEquals(2, ((List<Actor>) result).size());
		verify(dao).findAllBy(Sort.by("id"), Actor.class);
	}

	@Test
	void testGetByProjectionPageableClass() {
		List<Actor> actors = Arrays.asList(new Actor(1, "Pepito", "Grillo"), new Actor(2, "Carmelo", "Coton"));
		Page<Actor> page = new PageImpl<>(actors);
		when(dao.findAllBy(PageRequest.of(0, 10), Actor.class)).thenReturn(page);
		Page<Actor> result = srv.getByProjection(PageRequest.of(0, 10), Actor.class);
		assertEquals(2, result.getContent().size());
		verify(dao).findAllBy(PageRequest.of(0, 10), Actor.class);
	}

	@Test
	void testGetAllSort() {
		List<Actor> actors = Arrays.asList(new Actor(1, "Pepito", "Grillo"), new Actor(2, "Carmelo", "Coton"));
		when(dao.findAll(Sort.by("id"))).thenReturn(actors);
		Iterable<Actor> result = srv.getAll(Sort.by("id"));
		assertEquals(2, ((List<Actor>) result).size());
		verify(dao).findAll(Sort.by("id"));
	}

	@Test
	void testGetAllPageable() {
		List<Actor> actors = Arrays.asList(new Actor(1, "Pepito", "Grillo"), new Actor(2, "Carmelo", "Coton"));
		Page<Actor> page = new PageImpl<>(actors);
		when(dao.findAll(PageRequest.of(0, 10))).thenReturn(page);
		Page<Actor> result = srv.getAll(PageRequest.of(0, 10));
		assertEquals(2, result.getContent().size());
		verify(dao).findAll(PageRequest.of(0, 10));
	}

	@Test
	void testGetAll() {
		List<Actor> actors = Arrays.asList(new Actor(1, "Pepito", "Grillo"), new Actor(2, "Carmelo", "Coton"));
		when(dao.findAll()).thenReturn(actors);
		List<Actor> result = srv.getAll();
		assertEquals(2, result.size());
		verify(dao).findAll();
	}

	@Test
	void testGetOne() {
		Actor actor = new Actor(1, "Pepito", "Grillo");
		when(dao.findById(1)).thenReturn(Optional.of(actor));
		Optional<Actor> result = srv.getOne(1);
		assertTrue(result.isPresent());
		assertEquals("Pepito", result.get().getFirstName());
		verify(dao).findById(1);
	}

	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		Actor actor = new Actor(1, "Pepito", "Grillo");
		when(dao.existsById(1)).thenReturn(false);
		when(dao.save(actor)).thenReturn(actor);
		Actor result = srv.add(actor);
		assertEquals(actor, result);
		verify(dao).save(actor);
	}

	@Test
	void testModify() throws NotFoundException, InvalidDataException {
		Actor actor = new Actor(1, "Pepito", "Grillo");
		when(dao.existsById(1)).thenReturn(true);
		when(dao.save(actor)).thenReturn(actor);
		Actor result = srv.modify(actor);
		assertEquals(actor, result);
		verify(dao).save(actor);
	}

	@Test
	void testDelete() throws InvalidDataException {
		Actor actor = new Actor(1, "Pepito", "Grillo");
		srv.delete(actor);
		verify(dao).delete(actor);
	}

	@Test
	void testDeleteById() {
		srv.deleteById(1);
		verify(dao).deleteById(1);
	}

	@Test
	void testNovedades() {
		List<Actor> actors = Arrays.asList(new Actor(1, "Pepito", "Grillo"), new Actor(2, "Carmelo", "Coton"));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		when(dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(timestamp)).thenReturn(actors);
		List<Actor> result = srv.novedades(timestamp);
		assertEquals(2, result.size());
		verify(dao).findByLastUpdateGreaterThanEqualOrderByLastUpdate(timestamp);
	}

	@Test
	void testAddNullActor() {
		assertThrows(InvalidDataException.class, () -> {
			srv.add(null);
		});
	}

	@Test
	void testAddInvalidActor() {
		Actor actor = new Actor(0, "", "");
		assertThrows(InvalidDataException.class, () -> {
			srv.add(actor);
		});
	}

	@Test
	void testAddDuplicateActor() {
		Actor actor = new Actor(1, "Pepito", "Grillo");
		when(dao.existsById(1)).thenReturn(true);
		assertThrows(DuplicateKeyException.class, () -> {
			srv.add(actor);
		});
	}

	@Test
	void testModifyNullActor() {
		assertThrows(InvalidDataException.class, () -> {
			srv.modify(null);
		});
	}

	@Test
	void testModifyInvalidActor() {
		Actor actor = new Actor(0, "", "");
		assertThrows(InvalidDataException.class, () -> {
			srv.modify(actor);
		});
	}

	@Test
	void testModifyNotFoundActor() {
		Actor actor = new Actor(1, "Pepito", "Grillo");
		when(dao.existsById(1)).thenReturn(false);
		assertThrows(NotFoundException.class, () -> {
			srv.modify(actor);
		});
	}

	@Test
	void testDeleteNullActor() {
		assertThrows(InvalidDataException.class, () -> {
			srv.delete(null);
		});
	}
}
