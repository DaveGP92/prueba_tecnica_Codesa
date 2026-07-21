package com.codesa.test.controller;

import com.codesa.test.dto.TicketPurchaseRequest;
import com.codesa.test.dto.TicketRequest;
import com.codesa.test.dto.TicketResponse;
import com.codesa.test.service.TicketService;
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
public class TicketController extends BaseController {

    private static final Logger logger = LogManager.getLogger(TicketController.class);

    private final TicketService ticketService;

    @GetMapping("/ticket")
    public ResponseEntity<List<TicketResponse>> findAll() {
        logger.info("Iniciando consulta de tickets");

        List<TicketResponse> response = ticketService.findAll();

        logger.info("Consulta de tickets finalizada. Total: {}", response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ticket/paginated")
    public ResponseEntity<Page<TicketResponse>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Iniciando consulta paginada de tickets. Pagina: {}, tamaño: {}", page, size);

        Page<TicketResponse> response = ticketService.findAllPaged(page, size);

        logger.info("Consulta paginada de tickets finalizada. Elementos: {}", response.getNumberOfElements());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/ticket/purchase")
    public ResponseEntity<TicketResponse> purchase(@Valid @RequestBody TicketPurchaseRequest request) {
        logger.info("Iniciando compra pública de ticket");

        TicketResponse response = ticketService.purchase(request);

        logger.info("Compra pública de ticket finalizada con id: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/ticket/{id:\\d+}")
    public ResponseEntity<TicketResponse> findById(@PathVariable Long id) throws Exception {
        logger.info("Iniciando consulta de ticket por id: {}", id);

        TicketResponse response = ticketService.findById(id);

        logger.info("Consulta de ticket por id finalizada: {}", id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/ticket")
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody TicketRequest request) {
        logger.info("Iniciando creación de ticket");

        TicketResponse response = ticketService.create(request);

        logger.info("Ticket creado correctamente con id: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/ticket/{id:\\d+}")
    public ResponseEntity<TicketResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequest request) {

        logger.info("Iniciando actualización de ticket con id: {}", id);

        TicketResponse response = ticketService.update(id, request);

        logger.info("Ticket actualizado correctamente con id: {}", id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/ticket/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Iniciando eliminación de ticket con id: {}", id);

        ticketService.delete(id);

        logger.info("Ticket eliminado correctamente con id: {}", id);

        return ResponseEntity.noContent().build();
    }

}
