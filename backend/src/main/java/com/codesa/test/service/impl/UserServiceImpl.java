package com.codesa.test.service.impl;

import com.codesa.test.dto.UserRequest;
import com.codesa.test.dto.UserResponse;
import com.codesa.test.exception.BadRequestException;
import com.codesa.test.exception.ResourceNotFoundException;
import com.codesa.test.mapper.UserMapper;
import com.codesa.test.model.entity.User;
import com.codesa.test.model.repository.UserRepository;
import com.codesa.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> findAll() {
        logger.debug("Buscando todos los usuarios en base de datos");

       /* List<UserResponse> rsp2 =  new ArrayList<>();
        List<User> rsp = userRepository.findAll();
        for (User user : rsp) {
            UserResponse key = new UserResponse();
            key.setId(user.getId());
            key.setUserName(user.getUserName());

            rsp2.add(key);
        }

        return rsp2;*/


        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        logger.debug("Usuarios encontrados: {}", users.size());

        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> findAllPaged(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("La página no puede ser menor que cero");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor que cero");
        }

        logger.debug("Buscando usuarios paginados en base de datos. Pagina: {}, tamaño: {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<UserResponse> users = userRepository.findAllPaged(pageable)
                .map(userMapper::toResponse);

        logger.debug("Consulta paginada de usuarios finalizada. Elementos: {}", users.getNumberOfElements());

        return users;
    }

    @Override
    public UserResponse findById(Long id) {
        logger.debug("Buscando usuario por id: {}", id);

        User user = findUserById(id);

        logger.debug("Usuario encontrado con id: {}", id);

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse create(UserRequest request) {
        logger.debug("Preparando entidad usuario para guardar");

        validatePasswords(request);

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);

        logger.debug("Usuario guardado con id: {}", saved.getId());

        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        logger.debug("Buscando usuario para actualizar con id: {}", id);

        User user = findUserById(id);

        user.setUserName(request.getUserName());
        validatePasswords(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setRole(request.getRole().toUpperCase(Locale.ROOT));

        User saved = userRepository.save(user);

        logger.debug("Usuario actualizado con id: {}", saved.getId());

        return userMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Buscando usuario para eliminar con id: {}", id);

        User user = findUserById(id);

        userRepository.delete(user);

        logger.debug("Usuario eliminado con id: {}", id);
    }

    private void validatePasswords(UserRequest request) {
        if (!Objects.equals(request.getPassword(), request.getRepassword())) {
            logger.warn("Las contraseñas del request de usuario no coinciden");
            throw new BadRequestException("La contraseña y su confirmación deben coincidir");
        }
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuario no encontrado con id: {}", id);
                    return new ResourceNotFoundException("Usuario no encontrado con id: " + id);
                });
    }

}
