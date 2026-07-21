package com.codesa.test.mapper;

import com.codesa.test.dto.PlayRequest;
import com.codesa.test.dto.PlayResponse;
import com.codesa.test.model.entity.Play;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayMapper {

    public PlayResponse toResponse(Play entity) {

        PlayResponse response = new PlayResponse();

        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setDescription(entity.getDescription());

        if (entity.getGenre() != null) {
            response.setGenreId(entity.getGenre().getId());
            response.setGenreDescription(entity.getGenre().getDescription());
        }

        response.setDurationMinutes(entity.getDurationMinutes());
        response.setActive(entity.getActive());
        response.setCreatedAt(entity.getCreatedAt());

        return response;
    }

    public Play toEntity(PlayRequest request) {

        Play play = new Play();

        play.setTitle(request.getTitle());
        play.setDescription(request.getDescription());
        play.setDurationMinutes(request.getDurationMinutes());

        return play;
    }

}
