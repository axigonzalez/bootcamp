package com.catalogo.domains.contracts.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import com.catalogo.domains.entities.Film;
import com.catalogo.domains.services.FilmServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilmRepositoryTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmServiceImpl filmService;

    @Test
    public void testFindByJPQL() {
        // Mocking repository method
        int filmId = 5;

        List<Film> films = Arrays.asList(
                new Film(5, "Film 1"),
                new Film(6, "Film 2"),
                new Film(7, "Film 3")
        );

        when(filmRepository.findByJPQL(filmId)).thenReturn(films);

        List<Film> result = filmRepository.findByJPQL(filmId);

        assertEquals(3, result.size());
        assertEquals(5, result.get(0).getFilmId());
        assertEquals(6, result.get(1).getFilmId());
        assertEquals(7, result.get(2).getFilmId());

        verify(filmRepository, times(1)).findByJPQL(filmId);
    }

    @Test
    public void testFindBySQL() {
        // Mocking repository method
        int id = 5;

        List<Film> films = Arrays.asList(
                new Film(5, "Film 1"),
                new Film(6, "Film 2"),
                new Film(7, "Film 3")
        );

        when(filmRepository.findBySQL(id)).thenReturn(films);

        List<Film> result = filmRepository.findBySQL(id);

        assertEquals(3, result.size());
        assertEquals(5, result.get(0).getFilmId());
        assertEquals(6, result.get(1).getFilmId());
        assertEquals(7, result.get(2).getFilmId());

        verify(filmRepository, times(1)).findBySQL(id);
    }
}
