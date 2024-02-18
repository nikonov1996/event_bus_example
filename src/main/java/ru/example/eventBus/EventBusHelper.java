package ru.example.eventBus;


import com.codeborne.selenide.Selenide;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ru.example.eventBus.enums.EventBusEventTypeEnum;
import ru.example.eventBus.enums.EventBusMethodEnum;
import ru.example.eventBus.models.Detail;
import ru.example.eventBus.models.Event;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;


import static ru.example.eventBus.enums.EventBusEventTypeEnum.*;
import static ru.example.eventBus.enums.EventBusMethodEnum.*;


@UtilityClass
public class EventBusHelper {

    private final String EVENT_BUS = "crazy_game_event_bus";

    public void runDispatchEventMethod(Event event) {
        runEventBusMethodWithParams( DISPATCH_EVENT_METHOD, getDispatchEventMethodParams(event));
    }

    public Object runGetLastEventDetail(Event event) {
        return runEventBusMethodWithParams(GET_LAST_EVENT_DETAIL_METHOD, getLastEventDetailParams(event));
    }

    public Object getLastEventValues() {
        return getEventBusObj(LAST_EVENT_VALUES);
    }

    private Object runEventBusMethodWithParams(EventBusMethodEnum eventBusMethod, String eventBusMethodParameters) {
        return executeToEventBus(String.format("return window.%s.%s(%s);", EVENT_BUS, eventBusMethod.getEventBusMethod(), eventBusMethodParameters));
    }

    private Object getEventBusObj( EventBusMethodEnum eventBusMethod) {
        return executeToEventBus(String.format("return window.%s.%s;", EVENT_BUS, eventBusMethod.getEventBusMethod()));
    }

    private String getDispatchEventMethodParams(Event event) {
        var eventType = event.getType();
        var eventTemplate = "";
        if (eventType.equals(START_CRAZY_GAME_EVENT.getEvent())) {
            eventTemplate = getStartEventDetailTemplate(event.getDetail());
        } else if (eventType.equals(AGREE_RESULT_CRAZY_GAME_EVENT.getEvent())) {
            eventTemplate = getAgreeEventDetailTemplate(event.getDetail());
        } else if (eventType.equals(PROGRESS_CRAZY_GAME_EVENT.getEvent())) {
            eventTemplate = getProgressEventDetailTemplate(event.getDetail());
        } else {
            throw new RuntimeException(
                    String.format("Unknown event type [%s]. Existing events types in EventBus are: %s ", eventType,
                            Arrays.stream(EventBusEventTypeEnum.values())
                                    .map(EventBusEventTypeEnum::getEvent)
                                    .collect(Collectors.toList())));
        }
        return String.format("\"%s\" , %s", event.getType(), eventTemplate);
    }

    private String getLastEventDetailParams(Event event) {
        return String.format("\"%s\"", event.getType());
    }

    private String getStartEventDetailTemplate(Detail detail) {
        return readFile("eventBus/eventBusStartEventTemplate.js")
                .replace("__DETAIL_STATUS_VALUE__", detail.getStatus())
                .replace("__DETAIL_ALL_STEPS_VALUE__", String.valueOf(detail.getAllSteps()));
    }

    private String getProgressEventDetailTemplate(Detail detail) {
        return readFile("eventBus/eventBusProgressEventTemplate.js")
                .replace("__DETAIL_STATUS_VALUE__", detail.getStatus())
                .replace("__DETAIL_ALL_STEPS_VALUE__", String.valueOf(detail.getAllSteps()))
                .replace("__DETAIL_CURRENT_STEP_VALUE__", String.valueOf(detail.getCurrentStep()));
    }

    private String getAgreeEventDetailTemplate(Detail detail) {
        return readFile("eventBus/eventBusAgreeResultEventTemplate.js")
                .replace("__DETAIL_AGREE_STATUS_VALUE__", String.valueOf(detail.getAgree()));
    }

    @SneakyThrows(IOException.class)
    private String readFile(String fileName) {
        StringBuilder resultline = new StringBuilder();
        try (Scanner scanner = new Scanner(
                new File(Objects.requireNonNull(
                        EventBusHelper.class.getClassLoader().getResource(fileName)).getFile()))) {
            while (scanner.hasNextLine()) resultline.append(scanner.nextLine());
        }
        return resultline.toString();
    }

    private Object executeToEventBus(String content) {
        return Selenide.executeJavaScript(content);
    }

}

