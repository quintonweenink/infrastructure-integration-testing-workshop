package org.example.infrastructureintegrationtestingworkshop.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.infrastructureintegrationtestingworkshop.core.ReminderService;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderListener {

    private final ReminderService reminderService;

    @RabbitListener(queues = "notification.queue")
    public void listen(ReminderDto event) {
        log.info(event.toString());
        reminderService.createReminder(event);
    }
}
