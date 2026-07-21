package com.codesa.test.model.repository;

import com.codesa.test.model.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(
            value = "SELECT * FROM schedules",
            countQuery = "SELECT COUNT(*) FROM schedules",
            nativeQuery = true
    )
    Page<Schedule> findAllPaged(Pageable pageable);

    List<Schedule> findByActiveTrue();
}
