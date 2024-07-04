package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/actores/v1")
public class ActorResource {

    private ActorService srv;

    public ActorResource(ActorService srv) {
        this.srv = srv;
    }

    @Operation(summary = "Obtener todos los actores", description = "Permite obtener una lista de todos los actores en diferentes proyecciones.")
    @GetMapping
    public List<?> getAll(@RequestParam(required = false, defaultValue = "large") String modo) {
        if ("short".equals(modo))
            return srv.getByProjection(ActorShort.class);
        else {
            return srv.getByProjection(ActorDTO.class);
        }
    }

    @Operation(summary = "Obtener actores paginados", description = "Permite obtener una lista paginada de actores en proyección corta.")
    @GetMapping(params = "page")
    public Page<ActorShort> getAll(@Parameter(description = "Objeto paginación") Pageable page) {
        return srv.getByProjection(page, ActorShort.class);
    }

    @Operation(summary = "Obtener un actor por ID", description = "Permite obtener un actor por su ID.")
    @GetMapping(path = "/{id}")
    public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return ActorDTO.from(item.get());
    }

    record Peli(int id, String titulo) {
    }

    @Operation(summary = "Obtener películas de un actor", description = "Permite obtener la lista de películas en las que ha actuado un actor.")
    @GetMapping(path = "/{id}/pelis")
    @Transactional
    public List<Peli> getFilms(@PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return item.get().getFilmActors().stream()
                .map(o -> new Peli(o.getFilm().getFilmId(), o.getFilm().getTitle()))
                .toList();
    }

    @Operation(summary = "Jubilar un actor", description = "Permite jubilar a un actor.")
    @DeleteMapping(path = "/{id}/jubilacion")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void jubilar(@PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        item.get().jubilate();
    }

    @Operation(summary = "Crear un nuevo actor", description = "Permite crear un nuevo actor.")
    @ApiResponse(responseCode = "201", description = "Actor creado exitosamente",
            content = @Content(schema = @Schema(implementation = ActorDTO.class)))
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody @Parameter(description = "ActorDTO") ActorDTO item)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = srv.add(ActorDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getActorId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Actualizar un actor", description = "Permite actualizar los datos de un actor por su ID.")
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (id != item.getActorId())
            throw new BadRequestException("No coinciden los identificadores");
        srv.modify(ActorDTO.from(item));
    }

    @Operation(summary = "Eliminar un actor", description = "Permite eliminar un actor por su ID.")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        srv.deleteById(id);
    }
}
