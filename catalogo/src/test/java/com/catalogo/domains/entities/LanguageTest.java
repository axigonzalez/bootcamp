package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

public class LanguageTest {

    private Language language;
    private Film film;

    @BeforeEach
    public void setUp() {
        language = new Language(1, "English");
        language.setFilms(new ArrayList<>());
        language.setFilmsVO(new ArrayList<>());
        film = new Film(1, "A description", (short) 2020, "A title");
    }

    @Test
    public void testAddFilm() {
        assertTrue(language.getFilms().isEmpty());

        language.addFilm(film);

        assertEquals(1, language.getFilms().size());
        assertTrue(language.getFilms().contains(film));
        assertEquals(language, film.getLanguage());
    }

    @Test
    public void testRemoveFilm() {
        language.addFilm(film);
        assertEquals(1, language.getFilms().size());

        language.removeFilm(film);

        assertTrue(language.getFilms().isEmpty());
        assertNull(film.getLanguage());
    }

    @Test
    public void testAddFilmsVO() {
        assertTrue(language.getFilmsVO().isEmpty());

        language.addFilmsVO(film);

        assertEquals(1, language.getFilmsVO().size());
        assertTrue(language.getFilmsVO().contains(film));
        assertEquals(language, film.getLanguageVO());
    }

    @Test
    public void testRemoveFilmsVO() {
        language.addFilmsVO(film);
        assertEquals(1, language.getFilmsVO().size());

        language.removeFilmsVO(film);

        assertTrue(language.getFilmsVO().isEmpty());
        assertNull(film.getLanguageVO());
    }
}
