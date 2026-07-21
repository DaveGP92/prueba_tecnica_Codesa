package com.codesa.test.mapper;

import com.codesa.test.dto.GenreRequest;
import com.codesa.test.dto.GenreResponse;
import com.codesa.test.model.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenreMapper {

    public GenreResponse toResponse(Genre entity) {

        GenreResponse response = new GenreResponse();

        response.setId(entity.getId());
        response.setDescription(entity.getDescription());

        return response;
    }

    public Genre toEntity(GenreRequest request) {

        Genre entity = new Genre();
        entity.setDescription(request.getDescription());

        return entity;
    }

}
