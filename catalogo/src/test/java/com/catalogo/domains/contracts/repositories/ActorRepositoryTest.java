package com.catalogo.domains.contracts.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import com.catalogo.domains.entities.Actor;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.domains.services.ActorServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActorRepositoryTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorServiceImpl actorService;

    @Test
    public void testFindTop5ByLastNameStartingWithOrderByFirstNameDesc() {

        String prefix = "Doe";
        Sort orderBy = Sort.by(Sort.Order.desc("firstName"));

        when(actorRepository.findTop5ByLastNameStartingWithOrderByFirstNameDesc(prefix)).thenReturn(Arrays.asList(
                new Actor(1, "John", "Doe"),
                new Actor(2, "Jane", "Doe"),
                new Actor(3, "Alex", "Doe")
        ));

        List<Actor> actors = actorRepository.findTop5ByLastNameStartingWithOrderByFirstNameDesc(prefix);

        assertEquals(3, actors.size());
        assertEquals("John", actors.get(0).getFirstName());
        assertEquals("Jane", actors.get(1).getFirstName());
        assertEquals("Alex", actors.get(2).getFirstName());

        verify(actorRepository, times(1)).findTop5ByLastNameStartingWithOrderByFirstNameDesc(prefix);
    }

    @Test
    public void testFindTop5ByLastNameStartingWith() {

        String prefix = "Doe";
        Sort orderBy = Sort.by(Sort.Order.asc("firstName"));

        when(actorRepository.findTop5ByLastNameStartingWith(prefix, orderBy)).thenReturn(Arrays.asList(
                new Actor(1, "John", "Doe"),
                new Actor(2, "Jane", "Doe"),
                new Actor(3, "Alex", "Doe")
        ));

        List<Actor> actors = actorRepository.findTop5ByLastNameStartingWith(prefix, orderBy);

        assertEquals(3, actors.size());
        assertEquals("John", actors.get(0).getFirstName());
        assertEquals("Jane", actors.get(1).getFirstName());
        assertEquals("Alex", actors.get(2).getFirstName());

        verify(actorRepository, times(1)).findTop5ByLastNameStartingWith(prefix, orderBy);
    }

    @Test
    public void testFindByActorIdGreaterThanEqual() {

        int actorId = 5;

        when(actorRepository.findByActorIdGreaterThanEqual(actorId)).thenReturn(Arrays.asList(
                new Actor(5, "John", "Doe"),
                new Actor(6, "Jane", "Smith"),
                new Actor(7, "Alex", "Johnson")
        ));

        List<Actor> actors = actorRepository.findByActorIdGreaterThanEqual(actorId);

        assertEquals(3, actors.size());
        assertEquals(5, actors.get(0).getActorId());
        assertEquals(6, actors.get(1).getActorId());
        assertEquals(7, actors.get(2).getActorId());

        verify(actorRepository, times(1)).findByActorIdGreaterThanEqual(actorId);
    }

    @Test
    public void testFindByJPQL() {
        int actorId = 5;

        when(actorRepository.findByJPQL(actorId)).thenReturn(Arrays.asList(
                new Actor(5, "John", "Doe"),
                new Actor(6, "Jane", "Smith"),
                new Actor(7, "Alex", "Johnson")
        ));

        List<Actor> actors = actorRepository.findByJPQL(actorId);

        assertEquals(3, actors.size());
        assertEquals(5, actors.get(0).getActorId());
        assertEquals(6, actors.get(1).getActorId());
        assertEquals(7, actors.get(2).getActorId());

        verify(actorRepository, times(1)).findByJPQL(actorId);
    }

    @Test
    public void testFindBySQL() {
        int id = 5;

        when(actorRepository.findBySQL(id)).thenReturn(Arrays.asList(
                new Actor(5, "John", "Doe"),
                new Actor(6, "Jane", "Smith"),
                new Actor(7, "Alex", "Johnson")
        ));

        List<Actor> actors = actorRepository.findBySQL(id);

        assertEquals(3, actors.size());
        assertEquals(5, actors.get(0).getActorId());
        assertEquals(6, actors.get(1).getActorId());
        assertEquals(7, actors.get(2).getActorId());

        verify(actorRepository, times(1)).findBySQL(id);
    }


}
