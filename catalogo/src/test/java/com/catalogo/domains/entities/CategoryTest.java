package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category(1, "Comedy");
        category.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        category.setFilmCategories(new ArrayList<>());
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, category.getCategoryId());
        assertEquals("Comedy", category.getName());
    }

    @Test
    public void testSetters() {
        category.setCategoryId(2);
        category.setName("Drama");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        category.setLastUpdate(now);

        assertEquals(2, category.getCategoryId());
        assertEquals("Drama", category.getName());
        assertEquals(now, category.getLastUpdate());
    }

    @Test
    public void testAddFilmCategory() {
        FilmCategory filmCategory = mock(FilmCategory.class);
        when(filmCategory.getCategory()).thenReturn(category);

        category.addFilmCategory(filmCategory);

        List<FilmCategory> filmCategories = category.getFilmCategories();
        assertEquals(1, filmCategories.size());
        assertTrue(filmCategories.contains(filmCategory));
        verify(filmCategory).setCategory(category);
    }

    @Test
    public void testRemoveFilmCategory() {
        FilmCategory filmCategory = mock(FilmCategory.class);
        when(filmCategory.getCategory()).thenReturn(category);

        category.addFilmCategory(filmCategory);
        category.removeFilmCategory(filmCategory);

        List<FilmCategory> filmCategories = category.getFilmCategories();
        assertEquals(0, filmCategories.size());
        assertFalse(filmCategories.contains(filmCategory));
        verify(filmCategory).setCategory(null);
    }

    @Test
    public void testEqualsAndHashCode() {
        Category category1 = new Category(1, "Comedy");
        Category category2 = new Category(1, "Comedy");
        Category category3 = new Category(2, "Drama");

        assertEquals(category1, category2);
        assertNotEquals(category1, category3);

        assertEquals(category1.hashCode(), category2.hashCode());
        assertNotEquals(category1.hashCode(), category3.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Category [categoryId=1, lastUpdate=" + category.getLastUpdate() + ", name=Comedy, filmCategories=[]]";
        assertEquals(expected, category.toString());
    }
}

