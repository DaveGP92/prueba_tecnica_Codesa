package com.codesa.test.controller;

import com.codesa.test.dto.LoginRequest;
import com.codesa.test.dto.LoginResponse;
import com.codesa.test.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Iniciando autenticación de usuario: {}", request.getUserName());

        LoginResponse response = authService.login(request);

        logger.info("Autenticación exitosa para usuario: {}", request.getUserName());

        return ResponseEntity.ok(response);
    }

}
