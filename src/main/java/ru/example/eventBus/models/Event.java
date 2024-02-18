package ru.example.eventBus.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Event {
    @EqualsAndHashCode.Exclude
    private Detail detail;
    private String type;
}
