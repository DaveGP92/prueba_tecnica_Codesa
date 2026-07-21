package com.codesa.test.service;

import com.codesa.test.dto.UserRequest;
import com.codesa.test.dto.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<UserResponse> findAll();
    Page<UserResponse> findAllPaged(int page, int size);
    UserResponse findById(Long id) throws Exception;
    UserResponse create(UserRequest request);
    UserResponse update(Long id, UserRequest request);
    void delete(Long id);
}
