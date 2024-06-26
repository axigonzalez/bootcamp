package com.catalogo.domains.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.catalogo.domains.contracts.repositories.ActorRepository;
import com.catalogo.domains.entities.Actor;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActorServiceImplTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorServiceImpl actorService;

    private Actor actor1;
    private Actor actor2;

    @BeforeEach
    public void setUp() {
        actor1 = new Actor(1, "John", "Doe");
        actor2 = new Actor(2, "Jane", "Smith");
    }

    @Test
    public void testGetByProjection() {
        // Mocking repository method
        when(actorRepository.findAllBy(String.class)).thenReturn(Arrays.asList("John Doe", "Jane Smith"));

        List<String> actors = actorService.getByProjection(String.class);

        assertEquals(2, actors.size());
        assertEquals("John Doe", actors.get(0));
        assertEquals("Jane Smith", actors.get(1));

        verify(actorRepository, times(1)).findAllBy(String.class);
    }

    @Test
    public void testGetByProjectionWithSort() {
        // Mocking repository method
        Sort sort = Sort.by(Sort.Order.asc("lastName"));
        when(actorRepository.findAllBy(sort, String.class)).thenReturn(Arrays.asList("Doe", "Smith"));

        List<String> actors = (List<String>) actorService.getByProjection(sort, String.class);

        assertEquals(2, actors.size());
        assertEquals("Doe", actors.get(0));
        assertEquals("Smith", actors.get(1));

        verify(actorRepository, times(1)).findAllBy(sort, String.class);
    }

    @Test
    public void testGetByProjectionWithPageable() {
        // Mocking repository method
        Pageable pageable = Pageable.unpaged();
        Page<String> page = new PageImpl<>(Arrays.asList("John", "Jane"), pageable, 2);
        when(actorRepository.findAllBy(pageable, String.class)).thenReturn(page);

        Page<String> resultPage = (Page<String>) actorService.getByProjection(pageable, String.class);

        assertEquals(2, resultPage.getTotalElements());
        assertEquals("John", resultPage.getContent().get(0));
        assertEquals("Jane", resultPage.getContent().get(1));

        verify(actorRepository, times(1)).findAllBy(pageable, String.class);
    }

    @Test
    public void testGetAllWithSort() {
        // Mocking repository method
        Sort sort = Sort.by(Sort.Order.asc("lastName"));
        when(actorRepository.findAll(sort)).thenReturn(Arrays.asList(actor1, actor2));

        Iterable<Actor> actors = actorService.getAll(sort);

        assertEquals(2, ((List<Actor>) actors).size());
        assertEquals(actor1, ((List<Actor>) actors).get(0));
        assertEquals(actor2, ((List<Actor>) actors).get(1));

        verify(actorRepository, times(1)).findAll(sort);
    }

    @Test
    public void testGetAllWithPageable() {
        // Mocking repository method
        Pageable pageable = Pageable.unpaged();
        Page<Actor> page = new PageImpl<>(Arrays.asList(actor1, actor2), pageable, 2);
        when(actorRepository.findAll(pageable)).thenReturn(page);

        Page<Actor> resultPage = actorService.getAll(pageable);

        assertEquals(2, resultPage.getTotalElements());
        assertEquals(actor1, resultPage.getContent().get(0));
        assertEquals(actor2, resultPage.getContent().get(1));

        verify(actorRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetAll() {
        // Mocking repository method
        when(actorRepository.findAll()).thenReturn(Arrays.asList(actor1, actor2));

        List<Actor> actors = actorService.getAll();

        assertEquals(2, actors.size());
        assertEquals(actor1, actors.get(0));
        assertEquals(actor2, actors.get(1));

        verify(actorRepository, times(1)).findAll();
    }

    @Test
    public void testGetOne() {
        // Mocking repository method
        when(actorRepository.findById(1)).thenReturn(Optional.of(actor1));
        when(actorRepository.findById(2)).thenReturn(Optional.of(actor2));

        Optional<Actor> actorOptional1 = actorService.getOne(1);
        Optional<Actor> actorOptional2 = actorService.getOne(2);

        assertTrue(actorOptional1.isPresent());
        assertEquals(actor1, actorOptional1.get());

        assertTrue(actorOptional2.isPresent());
        assertEquals(actor2, actorOptional2.get());

        verify(actorRepository, times(1)).findById(1);
        verify(actorRepository, times(1)).findById(2);
    }

    @Test
    public void testAdd() {
        Actor newActor = new Actor(3, "jhon", "salchichon");

        // Mocking repository method
        when(actorRepository.save(newActor)).thenReturn(newActor);

        try {
            Actor addedActor = actorService.add(newActor);
            assertEquals(newActor, addedActor);

            verify(actorRepository, times(1)).save(newActor);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testAddThrowsDuplicateKeyException() {
        Actor existingActor = new Actor(1, "John", "Doe");

        // Mocking repository method
        when(actorRepository.existsById(existingActor.getActorId())).thenReturn(true);

        try {
            actorService.add(existingActor);
            fail("DuplicateKeyException should have been thrown");
        } catch (DuplicateKeyException e) {
            // Expected exception
            assertEquals("Ya existe", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type thrown");
        }
    }

    @Test
    public void testAddThrowsInvalidDataException() {
        Actor invalidActor = new Actor(); // Assuming this actor is invalid

        try {
            actorService.add(invalidActor);
            fail("InvalidDataException should have been thrown");
        } catch (InvalidDataException e) {
            // Expected exception
            assertNotNull(e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type thrown");
        }
    }

    @Test
    public void testModify() {
        Actor modifiedActor = new Actor(1, "Modified", "Actor");

        // Mocking repository method
        when(actorRepository.existsById(modifiedActor.getActorId())).thenReturn(true);
        when(actorRepository.save(modifiedActor)).thenReturn(modifiedActor);

        try {
            Actor result = actorService.modify(modifiedActor);
            assertEquals(modifiedActor, result);

            verify(actorRepository, times(1)).save(modifiedActor);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testModifyThrowsNotFoundException() {
        Actor nonExistingActor = new Actor(999, "Non Existing", "Actor");

        // Mocking repository method
        when(actorRepository.existsById(nonExistingActor.getActorId())).thenReturn(false);

        try {
            actorService.modify(nonExistingActor);
            fail("NotFoundException should have been thrown");
        } catch (NotFoundException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Unexpected exception type thrown");
        }
    }

    @Test
    public void testModifyThrowsInvalidDataException() {
        Actor invalidActor = new Actor(); // Assuming this actor is invalid

        try {
            actorService.modify(invalidActor);
            fail("InvalidDataException should have been thrown");
        } catch (InvalidDataException e) {
            // Expected exception
            assertNotNull(e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type thrown");
        }
    }

    @Test
    public void testDelete() {
        Actor actorToDelete = new Actor(1, "John", "Doe");

        // No need to mock repository method for delete since it doesn't return anything

        try {
            actorService.delete(actorToDelete);
            verify(actorRepository, times(1)).delete(actorToDelete);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testDeleteThrowsInvalidDataException() {
        Actor invalidActor = null; // Assuming this actor is null

        try {
            actorService.delete(invalidActor);
            fail("InvalidDataException should have been thrown");
        } catch (InvalidDataException e) {
            // Expected exception
            assertNotNull(e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type thrown");
        }
    }

    @Test
    public void testDeleteById() {
        int actorIdToDelete = 1;

        // No need to mock repository method for deleteById since it doesn't return anything

        try {
            actorService.deleteById(actorIdToDelete);
            verify(actorRepository, times(1)).deleteById(actorIdToDelete);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testRepartePremios() {
        // Test implementation pending
    }
}

