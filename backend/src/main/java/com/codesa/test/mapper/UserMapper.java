package com.codesa.test.mapper;

import com.codesa.test.dto.UserRequest;
import com.codesa.test.dto.UserResponse;
import com.codesa.test.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponse toResponse(User entity) {

        UserResponse response = new UserResponse();

        response.setId(entity.getId());
        response.setUserName(entity.getUserName());
        response.setEmail(entity.getEmail());
        response.setFullName(entity.getFullName());
        response.setRole(entity.getRole());
        response.setCreatedAt(entity.getCreatedAt());

        return response;
    }

    public User toEntity(UserRequest request) {

        User user = new User();

        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setRole(request.getRole().toUpperCase(Locale.ROOT));

        return user;
    }

}
