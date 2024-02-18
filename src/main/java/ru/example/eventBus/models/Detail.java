package ru.example.eventBus.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class Detail {
    private String status;
    private Integer currentStep;
    private Integer allSteps;
    private Boolean agree;
}
