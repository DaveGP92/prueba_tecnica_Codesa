package com.codesa.test.controller;

import com.codesa.test.dto.GenreRequest;
import com.codesa.test.dto.GenreResponse;
import com.codesa.test.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController extends BaseController {

    private static final Logger logger = LogManager.getLogger(GenreController.class);

    private final GenreService genreService;

    @GetMapping("/genre")
    public ResponseEntity<List<GenreResponse>> findAll() {
        logger.info("Iniciando consulta de géneros");

        List<GenreResponse> response = genreService.findAll();

        logger.info("Consulta de géneros finalizada. Total: {}", response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/genre/paginated")
    public ResponseEntity<Page<GenreResponse>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Iniciando consulta paginada de géneros. Pagina: {}, tamaño: {}", page, size);

        Page<GenreResponse> response = genreService.findAllPaged(page, size);

        logger.info("Consulta paginada de géneros finalizada. Elementos: {}", response.getNumberOfElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/genre/{id:\\d+}")
    public ResponseEntity<GenreResponse> findById(@PathVariable Long id) throws Exception {
        logger.info("Iniciando consulta de género por id: {}", id);

        GenreResponse response = genreService.findById(id);

        logger.info("Consulta de género por id finalizada: {}", id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/genre")
    public ResponseEntity<GenreResponse> create(@Valid @RequestBody GenreRequest request) {
        logger.info("Iniciando creación de género");

        GenreResponse response = genreService.create(request);

        logger.info("Género creado correctamente con id: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/genre/{id:\\d+}")
    public ResponseEntity<GenreResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody GenreRequest request) {

        logger.info("Iniciando actualización de género con id: {}", id);

        GenreResponse response = genreService.update(id, request);

        logger.info("Género actualizado correctamente con id: {}", id);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/genre/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Iniciando eliminación de género con id: {}", id);

        genreService.delete(id);

        logger.info("Género eliminado correctamente con id: {}", id);

        return ResponseEntity.noContent().build();
    }

}
