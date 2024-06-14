package org.example.infrastructureintegrationtestingworkshop.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderSender {

    private final AmqpTemplate amqpTemplate;

    public void sendEvent(ReminderDto event) {
        amqpTemplate.convertAndSend("amq.topic", "reminder.created", event);
    }
}
