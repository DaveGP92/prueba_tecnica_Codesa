package com.codesa.test.mapper;

import com.codesa.test.dto.ScheduleRequest;
import com.codesa.test.dto.ScheduleResponse;
import com.codesa.test.model.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleMapper {

    public ScheduleResponse toResponse(Schedule entity) {

        ScheduleResponse response = new ScheduleResponse();

        response.setId(entity.getId());

        if (entity.getPlay() != null) {
            response.setPlayId(entity.getPlay().getId());
            response.setPlayTitle(entity.getPlay().getTitle());
        }

        response.setDateTime(entity.getDateTime());
        response.setRoom(entity.getRoom());
        response.setTotalSeats(entity.getTotalSeats());
        response.setAvailableSeats(entity.getAvailableSeats());
        response.setBasePrice(entity.getBasePrice());
        response.setActive(entity.getActive());
        response.setCreatedAt(entity.getCreatedAt());

        return response;
    }

    public Schedule toEntity(ScheduleRequest request) {

        Schedule schedule = new Schedule();

        schedule.setDateTime(request.getDateTime());
        schedule.setRoom(request.getRoom());
        schedule.setTotalSeats(request.getTotalSeats());
        schedule.setAvailableSeats(request.getAvailableSeats());
        schedule.setBasePrice(request.getBasePrice());

        return schedule;
    }

}
