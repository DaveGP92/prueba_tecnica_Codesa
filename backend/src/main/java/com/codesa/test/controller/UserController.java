package com.codesa.test.controller;

import com.codesa.test.dto.UserRequest;
import com.codesa.test.dto.UserResponse;
import com.codesa.test.service.UserService;
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
public class UserController extends BaseController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<UserResponse>> findAll() {
        logger.info("Iniciando consulta de usuarios");

        List<UserResponse> response = userService.findAll();

        logger.info("Consulta de usuarios finalizada. Total: {}", response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/paginated")
    public ResponseEntity<Page<UserResponse>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Iniciando consulta paginada de usuarios. Pagina: {}, tamaño: {}", page, size);

        Page<UserResponse> response = userService.findAllPaged(page, size);

        logger.info("Consulta paginada de usuarios finalizada. Elementos: {}", response.getNumberOfElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{id:\\d+}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) throws Exception {
        logger.info("Iniciando consulta de usuario por id: {}", id);

        UserResponse response = userService.findById(id);

        logger.info("Consulta de usuario por id finalizada: {}", id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        logger.info("Iniciando creación de usuario");

        UserResponse response = userService.create(request);

        logger.info("Usuario creado correctamente con id: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/user/{id:\\d+}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        logger.info("Iniciando actualización de usuario con id: {}", id);

        UserResponse response = userService.update(id, request);

        logger.info("Usuario actualizado correctamente con id: {}", id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Iniciando eliminación de usuario con id: {}", id);

        userService.delete(id);

        logger.info("Usuario eliminado correctamente con id: {}", id);

        return ResponseEntity.noContent().build();
    }

}
