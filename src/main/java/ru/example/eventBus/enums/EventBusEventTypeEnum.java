package ru.example.eventBus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventBusEventTypeEnum {
    START_CRAZY_GAME_EVENT("crazy_game__event-bus__start"),
    PROGRESS_CRAZY_GAME_EVENT("crazy_game__event-bus__progress"),
    AGREE_RESULT_CRAZY_GAME_EVENT("crazy_game__event-bus__agree_result");

    private final String event;
}
