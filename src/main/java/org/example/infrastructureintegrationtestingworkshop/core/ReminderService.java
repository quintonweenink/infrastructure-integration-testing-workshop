package org.example.infrastructureintegrationtestingworkshop.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.infrastructureintegrationtestingworkshop.client.NotificationRestClient;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.example.infrastructureintegrationtestingworkshop.messaging.ReminderSender;
import org.example.infrastructureintegrationtestingworkshop.repository.ReminderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderService {

    private final NotificationRestClient notificationRestClient;
    private final ReminderSender reminderSender;
    private final ReminderRepository reminderRepository;

    public ReminderDto createReminder(ReminderDto reminderCreateDto) {
        reminderRepository.create(reminderCreateDto);
        notificationRestClient.sendNotification(reminderCreateDto);
        reminderSender.sendEvent(reminderCreateDto);
        return reminderCreateDto;
    }

    public List<ReminderDto> getReminders() {
        return reminderRepository.getAll();
    }
}
