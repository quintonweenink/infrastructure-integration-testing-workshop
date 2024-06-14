package org.example.infrastructureintegrationtestingworkshop.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.infrastructureintegrationtestingworkshop.core.ReminderService;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class ReminderController {

    private final ReminderService reminderService;

    @RequestMapping(
        method = {RequestMethod.POST},
        value = {"/reminders"},
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public ResponseEntity<ReminderDto> createReminder(@RequestBody ReminderDto reminderCreateDto) {
        ReminderDto reminder = reminderService.createReminder(reminderCreateDto);
        return ResponseEntity.ok(reminder);
    }

    @RequestMapping(
        method = {RequestMethod.GET},
        value = {"/reminders"},
        produces = {"application/json"}
    )
    public ResponseEntity<List<ReminderDto>> getReminders() {
        return ResponseEntity.ok(reminderService.getReminders());
    }
}