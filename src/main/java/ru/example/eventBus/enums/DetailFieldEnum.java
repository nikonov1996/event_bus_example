package ru.example.eventBus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetailFieldEnum {
    STATUS("status"),
    CURRENT_STEP("currentStep"),
    ALL_STEPS("totalSteps"),
    AGREE_STATUS("agree");

    private final String fieldName;
}
