package com.catalogo.domains.services;

import java.security.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.catalogo.domains.contracts.repositories.FilmRepository;
import com.catalogo.domains.contracts.services.FilmService;
import com.catalogo.domains.entities.Film;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

import io.micrometer.common.lang.NonNull;
import jakarta.transaction.Transactional;

@Service
public class FilmServiceImpl implements FilmService{
	private FilmRepository dao;

	public FilmServiceImpl(FilmRepository dao) {
		this.dao = dao;
	}

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Film> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Film> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Film> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(item.getFilmId() !=0 && dao.existsById(item.getFilmId()))
			throw new DuplicateKeyException("Ya existe");
		return dao.save(item);
	}

	@Override
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(!dao.existsById(item.getFilmId()))
			throw new NotFoundException();
		return dao.save(item);
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
    @Transactional
    public void save(Film film) {
        dao.save(film);
    }

	@Override
	public List<Film> novedades(@NonNull Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
