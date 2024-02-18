package ru.example.eventBus.providers;

import ru.example.eventBus.enums.EventBusEventTypeEnum;
import ru.example.eventBus.models.Detail;
import ru.example.eventBus.models.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.example.eventBus.enums.DetailFieldEnum.*;

public class EventBusObjectProvider {

    public Detail getGameDetail(Object eventObj) {
        if (eventObj != null) {
            Map<String, Object> map = (Map<String, Object>) eventObj;
            return getEventObjDetail(map);
        }
        return null;
    }

    public Event getEventDetailFromEventsListByType(Object eventListObj, EventBusEventTypeEnum eventType) {
        return getAllGameEventsList(eventListObj).stream()
                .filter(event -> event.getType().equals(eventType.getEvent()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format(
                        "В списке событий EventBus отсутствует событие с типом %s",
                        eventType.getEvent())));
    }

    public List<Event> getAllGameEventsList(Object eventListObj) {
        if (eventListObj != null) {
            Map<String, Object> map = (Map<String, Object>) eventListObj;
            List<Event> gameEventList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (isEventTypeExist(entry.getKey())) {
                    gameEventList.add(Event.builder()
                            .type(entry.getKey())
                            .detail(getGameDetail(entry.getValue()))
                            .build());
                }
            }
            return gameEventList;
        }
        return null;
    }

    private Detail getEventObjDetail(Map<String, Object> event) {
        var detail = new Detail();
        if ((event.containsKey(ALL_STEPS.getFieldName()))) {
            detail.setAllSteps((Integer) event.get(ALL_STEPS.getFieldName()));
        }
        if ((event.containsKey(STATUS.getFieldName()))) {
            detail.setStatus((String) event.get(STATUS.getFieldName()));
        }
        if ((event.containsKey(CURRENT_STEP.getFieldName()))) {
            detail.setCurrentStep((Integer) event.get(CURRENT_STEP.getFieldName()));
        }
        if ((event.containsKey(AGREE_STATUS.getFieldName()))) {
            detail.setAgree((Boolean) event.get(AGREE_STATUS.getFieldName()));
        }
        return detail;
    }

    private boolean isEventTypeExist(String eventType) {
        return Arrays.stream(EventBusEventTypeEnum.values())
                .map(EventBusEventTypeEnum::getEvent)
                .collect(Collectors.toList()).contains(eventType);
    }

}
