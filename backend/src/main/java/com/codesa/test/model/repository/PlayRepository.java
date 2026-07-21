package com.codesa.test.model.repository;

import com.codesa.test.model.entity.Play;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayRepository extends JpaRepository<Play, Long> {

    @Query(
            value = "SELECT * FROM plays",
            countQuery = "SELECT COUNT(*) FROM plays",
            nativeQuery = true
    )
    Page<Play> findAllPaged(Pageable pageable);
}
