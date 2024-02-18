package ru.example.eventBus.enums;
import lombok.*;
@Getter
@AllArgsConstructor
public enum EventBusMethodEnum {
    DISPATCH_EVENT_METHOD("dispatchEvent"),
    GET_LAST_EVENT_DETAIL_METHOD("getLastEventDetail"),
    LAST_EVENT_VALUES("lastEventValues");


    private final String eventBusMethod;
}
