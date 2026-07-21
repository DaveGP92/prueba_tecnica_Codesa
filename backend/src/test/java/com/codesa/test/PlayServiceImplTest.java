package com.codesa.test;

import com.codesa.test.dto.PlayRequest;
import com.codesa.test.dto.PlayResponse;
import com.codesa.test.exception.ResourceNotFoundException;
import com.codesa.test.mapper.PlayMapper;
import com.codesa.test.model.entity.Genre;
import com.codesa.test.model.entity.Play;
import com.codesa.test.model.repository.GenreRepository;
import com.codesa.test.model.repository.PlayRepository;
import com.codesa.test.service.impl.PlayServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayServiceImplTest {

    @Mock
    private PlayRepository playRepository;

    @Mock
    private PlayMapper playMapper;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private PlayServiceImpl playService;

    private Play play;
    private Genre genre;
    private PlayRequest request;
    private PlayResponse response;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setId(1L);
        genre.setDescription("Drama");

        play = new Play();
        play.setId(1L);
        play.setTitle("Hamlet");
        play.setDescription("Obra clasica");
        play.setDurationMinutes(120L);
        play.setGenre(genre);
        play.setActive(true);

        request = new PlayRequest();
        request.setTitle("Hamlet");
        request.setDescription("Obra clasica");
        request.setDurationMinutes(120L);
        request.setGenreId(1L);

        response = new PlayResponse();
        response.setId(1L);
        response.setTitle("Hamlet");
        response.setDescription("Obra clasica");
        response.setDurationMinutes(120L);
        response.setGenreId(1L);
        response.setGenreDescription("Drama");
        response.setActive(true);
    }

    @Test
    void findAllShouldReturnPlays() {
        when(playRepository.findAll()).thenReturn(List.of(play));
        when(playMapper.toResponse(play)).thenReturn(response);

        List<PlayResponse> result = playService.findAll();

        assertEquals(1, result.size());
        assertEquals("Hamlet", result.get(0).getTitle());
        verify(playRepository).findAll();
    }

    @Test
    void findAllPagedShouldReturnPage() {
        Page<Play> page = new PageImpl<>(List.of(play));

        when(playRepository.findAllPaged(any(Pageable.class))).thenReturn(page);
        when(playMapper.toResponse(play)).thenReturn(response);

        Page<PlayResponse> result = playService.findAllPaged(0, 10);

        assertEquals(1, result.getNumberOfElements());
        assertEquals("Hamlet", result.getContent().get(0).getTitle());
        verify(playRepository).findAllPaged(any(Pageable.class));
    }

    @Test
    void findByIdShouldReturnPlay() {
        when(playRepository.findById(1L)).thenReturn(Optional.of(play));
        when(playMapper.toResponse(play)).thenReturn(response);

        PlayResponse result = playService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Hamlet", result.getTitle());
    }

    @Test
    void findByIdShouldThrowExceptionWhenPlayDoesNotExist() {
        when(playRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> playService.findById(99L));
    }

    @Test
    void createShouldSavePlay() {
        when(playMapper.toEntity(request)).thenReturn(play);
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(playRepository.save(play)).thenReturn(play);
        when(playMapper.toResponse(play)).thenReturn(response);

        PlayResponse result = playService.create(request);

        assertEquals(1L, result.getId());
        assertTrue(play.getActive());
        verify(playRepository).save(play);
    }

    @Test
    void updateShouldUpdatePlay() {
        when(playRepository.findById(1L)).thenReturn(Optional.of(play));
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(playRepository.save(play)).thenReturn(play);
        when(playMapper.toResponse(play)).thenReturn(response);

        PlayResponse result = playService.update(1L, request);

        assertEquals("Hamlet", result.getTitle());
        verify(playRepository).save(play);
    }

    @Test
    void deleteShouldDeletePlay() {
        when(playRepository.findById(1L)).thenReturn(Optional.of(play));

        playService.delete(1L);

        verify(playRepository).delete(play);
    }
}
