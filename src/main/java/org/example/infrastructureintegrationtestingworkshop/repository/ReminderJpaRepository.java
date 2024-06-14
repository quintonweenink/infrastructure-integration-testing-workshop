package org.example.infrastructureintegrationtestingworkshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ReminderJpaRepository extends JpaRepository<ReminderEntity, Long> {
    List<ReminderEntity> findAll();
}