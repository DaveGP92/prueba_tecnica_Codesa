package com.codesa.test.service.impl;

import com.codesa.test.dto.GenreRequest;
import com.codesa.test.dto.GenreResponse;
import com.codesa.test.exception.ResourceNotFoundException;
import com.codesa.test.mapper.GenreMapper;
import com.codesa.test.model.entity.Genre;
import com.codesa.test.model.repository.GenreRepository;
import com.codesa.test.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private static final Logger logger = LogManager.getLogger(GenreServiceImpl.class);

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreResponse> findAll() {
        logger.debug("Buscando todos los géneros en base de datos");

        List<GenreResponse> genres = genreRepository.findAll()
                .stream()
                .map(genreMapper::toResponse)
                .toList();

        logger.debug("Géneros encontrados: {}", genres.size());

        return genres;
    }

    @Override
    public Page<GenreResponse> findAllPaged(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("La página no puede ser menor que cero");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor que cero");
        }

        logger.debug("Buscando géneros paginados en base de datos. Pagina: {}, tamaño: {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<GenreResponse> genres = genreRepository.findAllPaged(pageable)
                .map(genreMapper::toResponse);

        logger.debug("Consulta paginada de géneros finalizada. Elementos: {}", genres.getNumberOfElements());

        return genres;
    }

    @Override
    public GenreResponse findById(Long id) {
        logger.debug("Buscando género por id: {}", id);

        Genre genre = findGenreById(id);

        logger.debug("Género encontrado con id: {}", id);

        return genreMapper.toResponse(genre);
    }

    @Override
    public GenreResponse create(GenreRequest request) {
        logger.debug("Preparando entidad género para guardar");

        Genre genre = genreMapper.toEntity(request);

        Genre saved = genreRepository.save(genre);

        logger.debug("Género guardado con id: {}", saved.getId());

        return genreMapper.toResponse(saved);
    }

    @Override
    public GenreResponse update(Long id, GenreRequest request) {
        logger.debug("Buscando género para actualizar con id: {}", id);

        Genre genre = findGenreById(id);

        genre.setDescription(request.getDescription());

        Genre saved = genreRepository.save(genre);

        logger.debug("Género actualizado con id: {}", saved.getId());

        return genreMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Buscando género para eliminar con id: {}", id);

        Genre genre = findGenreById(id);

        genreRepository.delete(genre);

        logger.debug("Género eliminado con id: {}", id);
    }

    private Genre findGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Género no encontrado con id: {}", id);
                    return new ResourceNotFoundException("Género no encontrado con id: " + id);
                });
    }

}
