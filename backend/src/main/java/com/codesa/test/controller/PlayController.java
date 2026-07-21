package com.codesa.test.controller;

import com.codesa.test.dto.PlayRequest;
import com.codesa.test.dto.PlayResponse;
import com.codesa.test.service.PlayService;
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
public class PlayController extends BaseController {

    private static final Logger logger = LogManager.getLogger(PlayController.class);

    private final PlayService playService;

    @GetMapping("/play")
    public ResponseEntity<List<PlayResponse>> findAll() {
        logger.info("Iniciando consulta de obras");

        List<PlayResponse> response = playService.findAll();

        logger.info("Consulta de obras finalizada. Total: {}", response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/play/paginated")
    public ResponseEntity<Page<PlayResponse>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info(
                "Iniciando consulta paginada de obras. Pagina: {}, tamaño: {}",
                page,
                size
        );

        Page<PlayResponse> response = playService.findAllPaged(page, size);

        logger.info(
                "Consulta paginada de obras finalizada. Elementos: {}, total: {}",
                response.getNumberOfElements(),
                response.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/play/{id:\\d+}")
    public ResponseEntity<PlayResponse> findById(@PathVariable Long id) throws Exception {
        logger.info("Iniciando consulta de obra por id: {}", id);

        PlayResponse response = playService.findById(id);

        logger.info("Consulta de obra por id finalizada: {}", id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/play")
    public ResponseEntity<PlayResponse> create(@Valid @RequestBody PlayRequest request) {
        logger.info("Iniciando creación de obra");

        PlayResponse response = playService.create(request);

        logger.info("Obra creada correctamente con id: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/play/{id:\\d+}")
    public ResponseEntity<PlayResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PlayRequest request) {

        logger.info("Iniciando actualización de obra con id: {}", id);

        PlayResponse response = playService.update(id, request);

        logger.info("Obra actualizada correctamente con id: {}", id);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/play/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Iniciando eliminación de obra con id: {}", id);

        playService.delete(id);

        logger.info("Obra eliminada correctamente con id: {}", id);

        return ResponseEntity.noContent().build();
    }

}
