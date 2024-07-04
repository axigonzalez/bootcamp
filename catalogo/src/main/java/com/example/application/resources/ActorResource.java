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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Actores", description = "Operaciones relacionadas con los actores de películas")
@RequestMapping("/api/actores/v1")
public class ActorResource {

    private final ActorService srv;

    public ActorResource(ActorService srv) {
        this.srv = srv;
    }

    @Operation(summary = "Obtener todos los actores")
    @ApiResponse(responseCode = "200", description = "Lista de actores recuperados", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = ActorDTO.class)) })
    @GetMapping
    public List<?> getAll(
            @Parameter(description = "Modo de proyección de los actores", required = false, schema = @Schema(defaultValue = "large")) @RequestParam(required = false, defaultValue = "large") String modo) {
        if ("short".equals(modo))
            return srv.getByProjection(ActorShort.class);
        else {
            return srv.getByProjection(ActorDTO.class);
        }
    }

    @Operation(summary = "Obtener todos los actores paginados")
    @ApiResponse(responseCode = "200", description = "Lista paginada de actores recuperados", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) })
    @GetMapping(params = "page")
    public Page<ActorShort> getAll(Pageable page) {
        return srv.getByProjection(page, ActorShort.class);
    }

    @Operation(summary = "Obtener un actor por su ID")
    @ApiResponse(responseCode = "200", description = "Actor recuperado", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ActorDTO.class)) })
    @ApiResponse(responseCode = "404", description = "Actor no encontrado")
    @GetMapping(path = "/{id}")
    public ActorDTO getOne(
            @Parameter(description = "ID del actor a recuperar", required = true) @PathVariable int id)
            throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return ActorDTO.from(item.get());
    }

    record Peli(int id, String titulo) {
    }

    @Operation(summary = "Obtener películas en las que ha actuado un actor por su ID")
    @ApiResponse(responseCode = "200", description = "Lista de películas recuperadas", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Peli.class)) })
    @ApiResponse(responseCode = "404", description = "Actor no encontrado")
    @GetMapping(path = "/{id}/pelis")
    @Transactional
    public List<Peli> getFilms(
            @Parameter(description = "ID del actor para recuperar películas en las que ha actuado", required = true) @PathVariable int id)
            throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return item.get().getFilmActors().stream()
                .map(o -> new Peli(o.getFilm().getFilmId(), o.getFilm().getTitle()))
                .toList();
    }

    @Operation(summary = "Jubilar a un actor por su ID")
    @ApiResponse(responseCode = "202", description = "Actor jubilado")
    @ApiResponse(responseCode = "404", description = "Actor no encontrado")
    @DeleteMapping(path = "/{id}/jubilacion")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void jubilar(
            @Parameter(description = "ID del actor a jubilar", required = true) @PathVariable int id)
            throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        item.get().jubilate();
    }

    @Operation(summary = "Crear un nuevo actor")
    @ApiResponse(responseCode = "201", description = "Actor creado")
    @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    @ApiResponse(responseCode = "409", description = "Clave duplicada")
    @PostMapping
    public ResponseEntity<Object> create(
            @Parameter(description = "Datos del nuevo actor a crear", required = true) @Valid @RequestBody ActorDTO item)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = srv.add(ActorDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getActorId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Actualizar un actor existente")
    @ApiResponse(responseCode = "204", description = "Actor actualizado")
    @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    @ApiResponse(responseCode = "404", description = "Actor no encontrado")
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @Parameter(description = "ID del actor a actualizar", required = true) @PathVariable int id,
            @Parameter(description = "Datos actualizados del actor", required = true) @Valid @RequestBody ActorDTO item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (id != item.getActorId())
            throw new BadRequestException("No coinciden los identificadores");
        srv.modify(ActorDTO.from(item));
    }

    @Operation(summary = "Eliminar un actor existente")
    @ApiResponse(responseCode = "204", description = "Actor eliminado")
    @ApiResponse(responseCode = "404", description = "Actor no encontrado")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "ID del actor a eliminar", required = true) @PathVariable int id) {
        srv.deleteById(id);
    }
}
