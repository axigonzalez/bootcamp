package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.models.CategoryDTO;
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
@RequestMapping("/api/categorias/v1")
@Tag(name = "Categorías", description = "Operaciones relacionadas con las categorías de películas")
public class CategoryResource {

    private final CategoryService srv;

    public CategoryResource(CategoryService srv) {
        this.srv = srv;
    }

    @Operation(summary = "Obtener todas las categorías")
    @ApiResponse(responseCode = "200", description = "Lista de categorías recuperadas", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = CategoryDTO.class)) })
    @GetMapping
    public List<CategoryDTO> getAll() {
        return srv.getAll().stream().map(c -> CategoryDTO.from(c)).toList();
    }

    @Operation(summary = "Obtener una categoría por su ID")
    @ApiResponse(responseCode = "200", description = "Categoría recuperada", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class)) })
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @GetMapping(path = "/{id}")
    public CategoryDTO getOne(
            @Parameter(description = "ID de la categoría a recuperar", required = true) @PathVariable int id)
            throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return CategoryDTO.from(item.get());
    }

    record FilmRecord(int id, String titulo, String descripcion) {
    }

    @Operation(summary = "Obtener películas asociadas a una categoría por su ID")
    @ApiResponse(responseCode = "200", description = "Lista de películas asociadas recuperada", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = FilmRecord.class)) })
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @GetMapping(path = "/{id}/peliculas")
    @Transactional
    public List<FilmRecord> getFilms(
            @Parameter(description = "ID de la categoría para recuperar películas asociadas", required = true) @PathVariable int id)
            throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return item.get().getFilmCategories().stream()
                .map(o -> new FilmRecord(o.getFilm().getFilmId(), o.getFilm().getTitle(),
                        o.getFilm().getDescription()))
                .toList();
    }

    @Operation(summary = "Crear una nueva categoría")
    @ApiResponse(responseCode = "201", description = "Categoría creada")
    @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    @ApiResponse(responseCode = "409", description = "Clave duplicada")
    @PostMapping
    public ResponseEntity<Object> create(
            @Parameter(description = "Datos de la nueva categoría a crear", required = true) @Valid @RequestBody CategoryDTO item)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = srv.add(CategoryDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getCategoryId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Actualizar una categoría existente")
    @ApiResponse(responseCode = "204", description = "Categoría actualizada")
    @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @Parameter(description = "ID de la categoría a actualizar", required = true) @PathVariable int id,
            @Parameter(description = "Datos actualizados de la categoría", required = true) @Valid @RequestBody CategoryDTO item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (id != item.getCategoryId())
            throw new BadRequestException("No coinciden los identificadores");
        srv.modify(CategoryDTO.from(item));
    }

    @Operation(summary = "Eliminar una categoría existente")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "ID de la categoría a eliminar", required = true) @PathVariable int id) {
        srv.deleteById(id);
    }

}
