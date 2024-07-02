package com.example.application.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.application.resources.CategoryResource;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.models.CategoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.exceptions.NotFoundException;


@WebMvcTest(CategoryResource.class)
class CategoryResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService srv;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testGetAll() throws Exception {
		List<Category> categories = Arrays.asList(new Category(1, "Action"), new Category(2, "Comedy"),
				new Category(3, "Drama"));
		when(srv.getAll()).thenReturn(categories);

		mockMvc.perform(get("/api/categorias/v1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.size()").value(3))
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].nombre").value("Action"));
	}

	@Test
	void testGetOne() throws Exception {
		int categoryId = 1;
		Category category = new Category(categoryId, "Action");
		when(srv.getOne(categoryId)).thenReturn(Optional.of(category));

		mockMvc.perform(get("/api/categorias/v1/{id}", categoryId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").value(categoryId)).andExpect(jsonPath("$.nombre").value("Action"));
	}

	@Test
	void testGetOne404() throws Exception {
		int categoryId = 1;
		when(srv.getOne(categoryId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/categorias/v1/{id}", categoryId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.title").value("Not Found"));
	}

	@Test
	void testGetFilms() throws Exception {
		int categoryId = 1;
		Category category = new Category(categoryId, "Action");
		category.setFilmCategories(Arrays.asList(new FilmCategory(new Film(1, "Film 1", "Description 1"), category),
				new FilmCategory(new Film(2, "Film 2", "Description 2"), category)));
		when(srv.getOne(categoryId)).thenReturn(Optional.of(category));

		mockMvc.perform(get("/api/categorias/v1/{id}/peliculas", categoryId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.size()").value(2)).andExpect(jsonPath("$[0].id").exists())
				.andExpect(jsonPath("$[0].titulo").value("Film 1"))
				.andExpect(jsonPath("$[0].descripcion").value("Description 1")).andExpect(jsonPath("$[1].id").exists())
				.andExpect(jsonPath("$[1].titulo").value("Film 2"))
				.andExpect(jsonPath("$[1].descripcion").value("Description 2"));
	}

	@Test
	void testCreate() throws Exception {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setName("Action");

		Category category = new Category(1, "Action");
		when(srv.add(any())).thenReturn(category);

		mockMvc.perform(post("/api/categorias/v1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryDTO))).andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/api/categorias/v1/1"));
	}

	@Test
	void testUpdate() throws Exception {
		int categoryId = 1;
		CategoryDTO categoryDTO = new CategoryDTO(categoryId, "Updated Action");

		mockMvc.perform(put("/api/categorias/v1/{id}", categoryId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryDTO))).andExpect(status().isNoContent());
	}

	@Test
	void testDelete() throws Exception {
		int categoryId = 1;

		mockMvc.perform(delete("/api/categorias/v1/{id}", categoryId)).andExpect(status().isNoContent());
	}
}
