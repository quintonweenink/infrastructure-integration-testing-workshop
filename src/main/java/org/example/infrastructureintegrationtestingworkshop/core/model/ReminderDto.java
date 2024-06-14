package org.example.infrastructureintegrationtestingworkshop.core.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ReminderDto {
    private String message;
}
