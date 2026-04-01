package com.example.backend.repository;

import com.example.backend.model.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Beneficio b where b.id = :id")
    Optional<Beneficio> findByIdForUpdate(Long id);
}
