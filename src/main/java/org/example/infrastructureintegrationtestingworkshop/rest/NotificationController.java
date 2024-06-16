package org.example.infrastructureintegrationtestingworkshop.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class NotificationController {

    @RequestMapping(
        method = {RequestMethod.POST},
        value = {"/notifications/general"},
        consumes = {"application/json"}
    )
    public ResponseEntity<ReminderDto> createReminder(@RequestBody ReminderDto reminderCreateDto) {
        log.info("Notification: " + reminderCreateDto.getMessage());
        return ResponseEntity.ok(reminderCreateDto);
    }
}