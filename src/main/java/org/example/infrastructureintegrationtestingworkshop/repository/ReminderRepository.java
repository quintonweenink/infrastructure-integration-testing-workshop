package org.example.infrastructureintegrationtestingworkshop.repository;

import lombok.RequiredArgsConstructor;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReminderRepository {

    private final ReminderJpaRepository reminderJpaRepository;

    @Transactional
    public ReminderDto create(ReminderDto reminder) {
        ReminderEntity entity = new ReminderEntity();
        entity.setMessage(reminder.getMessage());
        reminderJpaRepository.save(entity);
        return reminder;
    }

    public List<ReminderDto> getAll() {
        return reminderJpaRepository.findAll().stream()
            .map(entity -> ReminderDto.builder()
                .message(entity.getMessage())
                .build())
            .toList();
    }

    public Optional<ReminderDto> findByMessage(String message) {
        return reminderJpaRepository.findFirstByMessage(message)
            .map(reminderEntity -> ReminderDto.builder().message(reminderEntity.getMessage()).build());
    }
}
