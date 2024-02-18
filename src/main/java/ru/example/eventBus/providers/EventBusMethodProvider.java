package ru.example.eventBus.providers;

import lombok.experimental.UtilityClass;
import ru.example.eventBus.models.Detail;
import ru.example.eventBus.models.Event;


import static ru.example.eventBus.enums.EventBusEventTypeEnum.START_CRAZY_GAME_EVENT;
import static ru.example.eventBus.enums.EventBusEventTypeEnum.PROGRESS_CRAZY_GAME_EVENT;
import static ru.example.eventBus.enums.EventBusEventTypeEnum.AGREE_RESULT_CRAZY_GAME_EVENT;


@UtilityClass
public class EventBusMethodProvider {

    public final String START_STATUS = "start";
    public final String PROGRESS_STATUS = "progress";

    public Event getStartGameEvent(Integer allStepsCount){
        return getStartGameEventBody(getStartGameDetail(allStepsCount));
    }

    public Event getProgressGameEvent(Integer curStep, Integer allSteps){
        return getProgressGameEventBody(getProgressGameDetail(curStep, allSteps));
    }
    public Event getAgreeResultEvent(Boolean isAgree){
        return getAgreeResultEventBody(getAgreeResultDetail(isAgree));
    }

    private Event getStartGameEventBody(Detail detail) {
        return Event.builder()
                .type(START_CRAZY_GAME_EVENT.getEvent())
                .detail(detail).build();
    }

    private Event getProgressGameEventBody(Detail detail) {
        return Event.builder()
                .type(PROGRESS_CRAZY_GAME_EVENT.getEvent())
                .detail(detail).build();
    }

    private Event getAgreeResultEventBody(Detail detail) {
        return Event.builder()
                .type(AGREE_RESULT_CRAZY_GAME_EVENT.getEvent())
                .detail(detail).build();
    }

    private Detail getStartGameDetail(Integer allSteps) {
        return Detail.builder()
                .status(START_STATUS)
                .allSteps(allSteps)
                .build();
    }

    private Detail getProgressGameDetail(Integer curStep, Integer allSteps) {
        return Detail.builder()
                .status(PROGRESS_STATUS)
                .currentStep(curStep)
                .allSteps(allSteps)
                .build();
    }

    private Detail getAgreeResultDetail(Boolean isAgree) {
        return Detail.builder()
                .agree(isAgree)
                .build();
    }

}
