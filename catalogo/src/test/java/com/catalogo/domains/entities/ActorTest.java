package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ActorTest {

    private Actor actor;

    @BeforeEach
    public void setUp() {
        actor = new Actor(1, "John", "Doe");
        actor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, actor.getActorId());
        assertEquals("John", actor.getFirstName());
        assertEquals("Doe", actor.getLastName());
    }

    @Test
    public void testSetters() {
        actor.setActorId(2);
        actor.setFirstName("Jane");
        actor.setLastName("Smith");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        actor.setLastUpdate(now);

        assertEquals(2, actor.getActorId());
        assertEquals("Jane", actor.getFirstName());
        assertEquals("Smith", actor.getLastName());
        assertEquals(now, actor.getLastUpdate());
    }

    @Test
    public void testAddFilmActor() {
        FilmActor filmActor = mock(FilmActor.class);
        when(filmActor.getActor()).thenReturn(actor);

        actor.addFilmActor(filmActor);

        List<FilmActor> filmActors = actor.getFilmActors();
        assertEquals(1, filmActors.size());
        assertTrue(filmActors.contains(filmActor));
        verify(filmActor).setActor(actor);
    }

    @Test
    public void testRemoveFilmActor() {
        FilmActor filmActor = mock(FilmActor.class);
        when(filmActor.getActor()).thenReturn(actor);

        actor.addFilmActor(filmActor);
        actor.removeFilmActor(filmActor);

        List<FilmActor> filmActors = actor.getFilmActors();
        assertEquals(0, filmActors.size());
        assertFalse(filmActors.contains(filmActor));
        verify(filmActor).setActor(null);
    }

    @Test
    public void testEqualsAndHashCode() {
        Actor actor1 = new Actor(1, "John", "Doe");
        Actor actor2 = new Actor(1, "John", "Doe");
        Actor actor3 = new Actor(2, "Jane", "Smith");

        assertEquals(actor1, actor2);
        assertNotEquals(actor1, actor3);

        assertEquals(actor1.hashCode(), actor2.hashCode());
        assertNotEquals(actor1.hashCode(), actor3.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Actor [actorId=1, firstName=John, lastName=Doe, lastUpdate=" + actor.getLastUpdate() + "]";
        assertEquals(expected, actor.toString());
    }
}
