package com.codesa.test.service.impl;

import com.codesa.test.dto.TicketPurchaseRequest;
import com.codesa.test.dto.TicketRequest;
import com.codesa.test.dto.TicketResponse;
import com.codesa.test.exception.BadRequestException;
import com.codesa.test.exception.ResourceNotFoundException;
import com.codesa.test.mapper.TicketMapper;
import com.codesa.test.model.entity.Schedule;
import com.codesa.test.model.entity.Ticket;
import com.codesa.test.model.entity.User;
import com.codesa.test.model.repository.ScheduleRepository;
import com.codesa.test.model.repository.TicketRepository;
import com.codesa.test.model.repository.UserRepository;
import com.codesa.test.service.TicketService;
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
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LogManager.getLogger(TicketServiceImpl.class);

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    public List<TicketResponse> findAll() {
        logger.debug("Buscando todos los tickets en base de datos");

        List<TicketResponse> tickets = ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toResponse)
                .toList();

        logger.debug("Tickets encontrados: {}", tickets.size());

        return tickets;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketResponse> findAllPaged(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("La página no puede ser menor que cero");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor que cero");
        }

        logger.debug("Buscando tickets paginados en base de datos. Pagina: {}, tamaño: {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<TicketResponse> tickets = ticketRepository.findAllPaged(pageable)
                .map(ticketMapper::toResponse);

        logger.debug("Consulta paginada de tickets finalizada. Elementos: {}", tickets.getNumberOfElements());

        return tickets;
    }

    @Override
    public TicketResponse findById(Long id) {
        logger.debug("Buscando ticket por id: {}", id);

        Ticket ticket = findTicketById(id);

        logger.debug("Ticket encontrado con id: {}", id);

        return ticketMapper.toResponse(ticket);
    }

    @Override
    public TicketResponse purchase(TicketPurchaseRequest request) {
        logger.debug("Iniciando compra pública de ticket");

        Schedule schedule = findScheduleById(request.getScheduleId());
        User user = null;

        if (request.getUserId() != null) {
            user = findUserById(request.getUserId());
        } else {
            validateCustomerData(request);
        }

        if (!Boolean.TRUE.equals(schedule.getActive())) {
            throw new BadRequestException("La función no está activa");
        }

        int availableSeats = getAvailableSeats(schedule);
        int quantity = request.getQuantity().intValue();

        if (quantity > availableSeats) {
            throw new BadRequestException("No hay asientos suficientes disponibles");
        }

        Float basePrice = getBasePrice(schedule);
        Float totalPrice = basePrice * quantity;

        Ticket ticket = new Ticket();
        ticket.setSchedule(schedule);
        ticket.setUser(user);
        ticket.setCustomerName(request.getCustomerName());
        ticket.setCustomerEmail(request.getCustomerEmail());
        ticket.setCustomerPhone(request.getCustomerPhone());
        ticket.setQuantity(request.getQuantity());
        ticket.setTotalPrice(totalPrice);
        ticket.setActive(true);

        schedule.setAvailableSeats(String.valueOf(availableSeats - quantity));
        scheduleRepository.save(schedule);

        Ticket saved = ticketRepository.save(ticket);

        logger.debug("Compra realizada correctamente con ticket id: {}", saved.getId());

        return ticketMapper.toResponse(saved);
    }

    @Override
    public TicketResponse create(TicketRequest request) {
        logger.debug("Preparando entidad ticket para guardar");

        Ticket ticket = ticketMapper.toEntity(request);

        if (request.getScheduleId() != null) {
            Schedule schedule = findScheduleById(request.getScheduleId());

            ticket.setSchedule(schedule);
        }

        if (request.getUserId() != null) {
            User user = findUserById(request.getUserId());

            ticket.setUser(user);
        } else {
            validateCustomerData(request.getCustomerName(), request.getCustomerEmail(), request.getCustomerPhone());
            ticket.setCustomerName(request.getCustomerName());
            ticket.setCustomerEmail(request.getCustomerEmail());
            ticket.setCustomerPhone(request.getCustomerPhone());
        }

        ticket.setActive(true);
        Ticket saved = ticketRepository.save(ticket);

        logger.debug("Ticket guardado con id: {}", saved.getId());

        return ticketMapper.toResponse(saved);
    }

    @Override
    public TicketResponse update(Long id, TicketRequest request) {
        logger.debug("Buscando ticket para actualizar con id: {}", id);

        Ticket ticket = findTicketById(id);

        ticket.setQuantity(request.getQuantity());
        ticket.setTotalPrice(request.getTotalPrice());

        if (request.getScheduleId() != null) {
            ticket.setSchedule(findScheduleById(request.getScheduleId()));
        } else {
            ticket.setSchedule(null);
        }

        if (request.getUserId() != null) {
            ticket.setUser(findUserById(request.getUserId()));
            ticket.setCustomerName(null);
            ticket.setCustomerEmail(null);
            ticket.setCustomerPhone(null);
        } else {
            ticket.setUser(null);
            validateCustomerData(request.getCustomerName(), request.getCustomerEmail(), request.getCustomerPhone());
            ticket.setCustomerName(request.getCustomerName());
            ticket.setCustomerEmail(request.getCustomerEmail());
            ticket.setCustomerPhone(request.getCustomerPhone());
        }

        Ticket saved = ticketRepository.save(ticket);

        logger.debug("Ticket actualizado con id: {}", saved.getId());

        return ticketMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Buscando ticket para eliminar con id: {}", id);

        Ticket ticket = findTicketById(id);

        ticketRepository.delete(ticket);

        logger.debug("Ticket eliminado con id: {}", id);
    }

    private Ticket findTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Ticket no encontrado con id: {}", id);
                    return new ResourceNotFoundException("Ticket no encontrado con id: " + id);
                });
    }

    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Función no encontrada con id: {}", id);
                    return new ResourceNotFoundException("Función no encontrada con id: " + id);
                });
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuario no encontrado con id: {}", id);
                    return new ResourceNotFoundException("Usuario no encontrado con id: " + id);
                });
    }

    private void validateCustomerData(TicketPurchaseRequest request) {
        validateCustomerData(request.getCustomerName(), request.getCustomerEmail(), request.getCustomerPhone());
    }

    private void validateCustomerData(String customerName, String customerEmail, String customerPhone) {
        if (isEmpty(customerName)) {
            throw new BadRequestException("El nombre del comprador es obligatorio");
        }

        if (isEmpty(customerEmail)) {
            throw new BadRequestException("El correo del comprador es obligatorio");
        }

        if (isEmpty(customerPhone)) {
            throw new BadRequestException("El celular del comprador es obligatorio");
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }

    private int getAvailableSeats(Schedule schedule) {
        try {
            return Integer.parseInt(schedule.getAvailableSeats());
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Los asientos disponibles no tienen un formato válido");
        }
    }

    private Float getBasePrice(Schedule schedule) {
        try {
            return Float.parseFloat(schedule.getBasePrice());
        } catch (NumberFormatException ex) {
            throw new BadRequestException("El precio base no tiene un formato válido");
        }
    }

}
