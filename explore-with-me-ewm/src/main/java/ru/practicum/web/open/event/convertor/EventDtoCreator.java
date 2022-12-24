package ru.practicum.web.open.event.convertor;

import ru.practicum.event.repository.entity.Event;
import ru.practicum.web.open.event.dto.EventDto;
import ru.practicum.web.open.event.dto.EventDtoInCollection;

public class EventDtoCreator {
    public EventDto create(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .eventDate(event.getEventDate())
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(event.getInitiator())
                .paid(event.getPaid())
                .views(event.getViews()) //TODO добавить поля
                .build();
    }
}
