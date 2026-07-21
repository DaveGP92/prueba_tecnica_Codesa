package com.codesa.test.service.impl;

import com.codesa.test.dto.ActiveScheduleResponse;
import com.codesa.test.dto.ScheduleRequest;
import com.codesa.test.dto.ScheduleResponse;
import com.codesa.test.exception.ResourceNotFoundException;
import com.codesa.test.mapper.ScheduleMapper;
import com.codesa.test.model.entity.Play;
import com.codesa.test.model.entity.Schedule;
import com.codesa.test.model.repository.PlayRepository;
import com.codesa.test.model.repository.ScheduleRepository;
import com.codesa.test.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger logger = LogManager.getLogger(ScheduleServiceImpl.class);

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final PlayRepository playRepository;

    @Override
    public List<ScheduleResponse> findAll() {
        logger.debug("Buscando todas las funciones en base de datos");

        List<ScheduleResponse> schedules = scheduleRepository.findAll()
                .stream()
                .map(scheduleMapper::toResponse)
                .toList();

        logger.debug("Funciónes encontradas: {}", schedules.size());

        return schedules;
    }

    @Override
    public List<ActiveScheduleResponse> findActiveSchedules() {
        logger.debug("Buscando funciones activas en base de datos");

        List<ActiveScheduleResponse> schedules = scheduleRepository.findByActiveTrue()
                .stream()
                .map(this::toActiveScheduleResponse)
                .toList();

        logger.debug("Funciónes activas encontradas: {}", schedules.size());

        return schedules;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleResponse> findAllPaged(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("La página no puede ser menor que cero");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor que cero");
        }

        logger.debug("Buscando funciones paginadas en base de datos. Pagina: {}, tamaño: {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<ScheduleResponse> schedules = scheduleRepository.findAllPaged(pageable)
                .map(scheduleMapper::toResponse);

        logger.debug("Consulta paginada de funciones finalizada. Elementos: {}", schedules.getNumberOfElements());

        return schedules;
    }

    @Override
    public ScheduleResponse findById(Long id) {
        logger.debug("Buscando función por id: {}", id);

        Schedule schedule = findScheduleById(id);

        logger.debug("Función encontrada con id: {}", id);

        return scheduleMapper.toResponse(schedule);
    }

    @Override
    public ScheduleResponse create(ScheduleRequest request) {
        logger.debug("Preparando entidad función para guardar");

        Schedule schedule = scheduleMapper.toEntity(request);

        if (request.getPlayId() != null) {
            Play play = findPlayById(request.getPlayId());

            schedule.setPlay(play);
        }

        schedule.setActive(true);
        Schedule saved = scheduleRepository.save(schedule);

        logger.debug("Función guardada con id: {}", saved.getId());

        return scheduleMapper.toResponse(saved);
    }

    @Override
    public ScheduleResponse update(Long id, ScheduleRequest request) {
        logger.debug("Buscando función para actualizar con id: {}", id);

        Schedule schedule = findScheduleById(id);

        schedule.setDateTime(request.getDateTime());
        schedule.setRoom(request.getRoom());
        schedule.setTotalSeats(request.getTotalSeats());
        schedule.setAvailableSeats(request.getAvailableSeats());
        schedule.setBasePrice(request.getBasePrice());

        if (request.getPlayId() != null) {
            schedule.setPlay(findPlayById(request.getPlayId()));
        } else {
            schedule.setPlay(null);
        }

        Schedule saved = scheduleRepository.save(schedule);

        logger.debug("Función actualizada con id: {}", saved.getId());

        return scheduleMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Buscando función para eliminar con id: {}", id);

        Schedule schedule = findScheduleById(id);

        scheduleRepository.delete(schedule);

        logger.debug("Función eliminada con id: {}", id);
    }

    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Función no encontrada con id: {}", id);
                    return new ResourceNotFoundException("Función no encontrada con id: " + id);
                });
    }

    private Play findPlayById(Long id) {
        return playRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Obra no encontrada con id: {}", id);
                    return new ResourceNotFoundException("Obra no encontrada con id: " + id);
                });
    }

    private ActiveScheduleResponse toActiveScheduleResponse(Schedule schedule) {
        ActiveScheduleResponse response = new ActiveScheduleResponse();

        response.setScheduleId(schedule.getId());
        response.setDateTime(schedule.getDateTime());
        response.setRoom(schedule.getRoom());
        response.setAvailableSeats(schedule.getAvailableSeats());
        response.setBasePrice(schedule.getBasePrice());

        if (schedule.getPlay() != null) {
            response.setPlayId(schedule.getPlay().getId());
            response.setPlayTitle(schedule.getPlay().getTitle());
            response.setPlayDescription(schedule.getPlay().getDescription());
            response.setDurationMinutes(schedule.getPlay().getDurationMinutes());

            if (schedule.getPlay().getGenre() != null) {
                response.setGenreId(schedule.getPlay().getGenre().getId());
                response.setGenreDescription(schedule.getPlay().getGenre().getDescription());
            }
        }

        return response;
    }

}
