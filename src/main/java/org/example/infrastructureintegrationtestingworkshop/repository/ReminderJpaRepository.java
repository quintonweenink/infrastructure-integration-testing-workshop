package org.example.infrastructureintegrationtestingworkshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface ReminderJpaRepository extends JpaRepository<ReminderEntity, Long> {
    List<ReminderEntity> findAll();

    Optional<ReminderEntity> findFirstByMessage(String message);

    @Query("SELECT")
}