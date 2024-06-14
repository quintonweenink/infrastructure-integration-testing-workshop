package org.example.infrastructureintegrationtestingworkshop.repository;

import lombok.RequiredArgsConstructor;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
