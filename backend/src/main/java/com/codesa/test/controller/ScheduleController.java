package com.codesa.test.controller;

import com.codesa.test.dto.ActiveScheduleResponse;
import com.codesa.test.dto.ScheduleRequest;
import com.codesa.test.dto.ScheduleResponse;
import com.codesa.test.service.ScheduleService;
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
public class ScheduleController extends BaseController {

    private static final Logger logger = LogManager.getLogger(ScheduleController.class);

    private final ScheduleService scheduleService;

    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduleResponse>> findAll() {
        logger.info("Iniciando consulta de funciones");

        List<ScheduleResponse> response = scheduleService.findAll();

        logger.info("Consulta de funciones finalizada. Total: {}", response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/schedule/active")
    public ResponseEntity<List<ActiveScheduleResponse>> findActiveSchedules() {
        logger.info("Iniciando consulta pública de funciones activas");

        List<ActiveScheduleResponse> response = scheduleService.findActiveSchedules();

        logger.info("Consulta pública de funciones activas finalizada. Total: {}", response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/schedule/paginated")
    public ResponseEntity<Page<ScheduleResponse>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Iniciando consulta paginada de funciones. Pagina: {}, tamaño: {}", page, size);

        Page<ScheduleResponse> response = scheduleService.findAllPaged(page, size);

        logger.info("Consulta paginada de funciones finalizada. Elementos: {}", response.getNumberOfElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/schedule/{id:\\d+}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable Long id) throws Exception {
        logger.info("Iniciando consulta de función por id: {}", id);

        ScheduleResponse response = scheduleService.findById(id);

        logger.info("Consulta de función por id finalizada: {}", id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponse> create(@Valid @RequestBody ScheduleRequest request) {
        logger.info("Iniciando creación de función");

        ScheduleResponse response = scheduleService.create(request);

        logger.info("Función creada correctamente con id: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/schedule/{id:\\d+}")
    public ResponseEntity<ScheduleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequest request) {

        logger.info("Iniciando actualización de función con id: {}", id);

        ScheduleResponse response = scheduleService.update(id, request);

        logger.info("Función actualizada correctamente con id: {}", id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/schedule/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Iniciando eliminación de función con id: {}", id);

        scheduleService.delete(id);

        logger.info("Función eliminada correctamente con id: {}", id);

        return ResponseEntity.noContent().build();
    }

}
