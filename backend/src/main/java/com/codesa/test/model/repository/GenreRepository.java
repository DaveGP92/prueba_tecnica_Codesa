package com.codesa.test.model.repository;

import com.codesa.test.model.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query(
            value = "SELECT * FROM genres",
            countQuery = "SELECT COUNT(*) FROM genres",
            nativeQuery = true
    )
    Page<Genre> findAllPaged(Pageable pageable);
}
