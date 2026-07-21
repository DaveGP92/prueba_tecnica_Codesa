package com.codesa.test.service;

import com.codesa.test.dto.LoginRequest;
import com.codesa.test.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
