package com.codesa.test.service.impl;

import com.codesa.test.dto.PlayRequest;
import com.codesa.test.dto.PlayResponse;
import com.codesa.test.exception.ResourceNotFoundException;
import com.codesa.test.mapper.PlayMapper;
import com.codesa.test.model.entity.Genre;
import com.codesa.test.model.entity.Play;
import com.codesa.test.model.repository.GenreRepository;
import com.codesa.test.model.repository.PlayRepository;
import com.codesa.test.service.PlayService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayServiceImpl implements PlayService {

    private static final Logger logger = LogManager.getLogger(PlayServiceImpl.class);

    private final PlayRepository playRepository;
    private final PlayMapper playMapper;
    private final GenreRepository genreRepository;

    @Override
    public List<PlayResponse> findAll() {
        logger.debug("Buscando todas las obras en base de datos");

        List<PlayResponse> plays = playRepository.findAll()
                .stream()
                .map(playMapper::toResponse)
                .toList();

        logger.debug("Obras encontradas: {}", plays.size());

        return plays;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlayResponse> findAllPaged(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("La página no puede ser menor que cero");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor que cero");
        }

        logger.debug(
                "Buscando obras paginadas en base de datos. Pagina: {}, tamaño: {}",
                page,
                size
        );

        Pageable pageable = PageRequest.of(page, size);

        Page<PlayResponse> plays = playRepository.findAllPaged(pageable)
                .map(playMapper::toResponse);

        logger.debug(
                "Consulta paginada de obras finalizada. Elementos: {}, total: {}",
                plays.getNumberOfElements(),
                plays.getTotalElements()
        );

        return plays;
    }

    @Override
    public PlayResponse findById(Long id) {
        logger.debug("Buscando obra por id: {}", id);

        Play play = findPlayById(id);

        logger.debug("Obra encontrada con id: {}", id);

        return playMapper.toResponse(play);
    }

    @Override
    public PlayResponse create(PlayRequest request) {
        logger.debug("Preparando entidad obra para guardar");

        Play play = playMapper.toEntity(request);

        if (request.getGenreId() != null) {
            Genre genre = findGenreById(request.getGenreId());

            play.setGenre(genre);
        }

        play.setActive(true);
        Play saved = playRepository.save(play);

        logger.debug("Obra guardada con id: {}", saved.getId());

        return playMapper.toResponse(saved);
    }

    @Override
    public PlayResponse update(Long id, PlayRequest request) {
        logger.debug("Buscando obra para actualizar con id: {}", id);

        Play play = findPlayById(id);

        play.setTitle(request.getTitle());
        play.setDescription(request.getDescription());
        play.setDurationMinutes(request.getDurationMinutes());

        if (request.getGenreId() != null) {
            play.setGenre(findGenreById(request.getGenreId()));
        } else {
            play.setGenre(null);
        }

        Play saved = playRepository.save(play);

        logger.debug("Obra actualizada con id: {}", saved.getId());

        return playMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Buscando obra para eliminar con id: {}", id);

        Play play = findPlayById(id);

        playRepository.delete(play);

        logger.debug("Obra eliminada con id: {}", id);
    }

    private Play findPlayById(Long id) {
        return playRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Obra no encontrada con id: {}", id);
                    return new ResourceNotFoundException("Obra no encontrada con id: " + id);
                });
    }

    private Genre findGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Género no encontrado con id: {}", id);
                    return new ResourceNotFoundException("Género no encontrado con id: " + id);
                });
    }

}
