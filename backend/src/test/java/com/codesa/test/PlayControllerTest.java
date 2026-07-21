package com.codesa.test;

import com.codesa.test.controller.PlayController;
import com.codesa.test.dto.PlayRequest;
import com.codesa.test.dto.PlayResponse;
import com.codesa.test.service.PlayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayControllerTest {

    @Mock
    private PlayService playService;

    @InjectMocks
    private PlayController playController;

    private PlayRequest request;
    private PlayResponse response;

    @BeforeEach
    void setUp() {
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
    void findAllShouldReturnOk() {
        when(playService.findAll()).thenReturn(List.of(response));

        ResponseEntity<List<PlayResponse>> result = playController.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void findAllPagedShouldReturnOk() {
        Page<PlayResponse> page = new PageImpl<>(List.of(response));

        when(playService.findAllPaged(0, 10)).thenReturn(page);

        ResponseEntity<Page<PlayResponse>> result = playController.findAllPaged(0, 10);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().getNumberOfElements());
    }

    @Test
    void findByIdShouldReturnOk() throws Exception {
        when(playService.findById(1L)).thenReturn(response);

        ResponseEntity<PlayResponse> result = playController.findById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void createShouldReturnCreated() {
        when(playService.create(request)).thenReturn(response);

        ResponseEntity<PlayResponse> result = playController.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Hamlet", result.getBody().getTitle());
    }

    @Test
    void updateShouldReturnOk() {
        when(playService.update(1L, request)).thenReturn(response);

        ResponseEntity<PlayResponse> result = playController.update(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void deleteShouldReturnNoContent() {
        doNothing().when(playService).delete(1L);

        ResponseEntity<Void> result = playController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(playService).delete(1L);
    }
}
