package ru.practicum.web.dto.event.convertor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.web.dto.event.EventDto;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventDtoConvertor {
    private final LocationConvertor locationConvertor;
    public EventDto convertToDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .eventDate(event.getEventDate())
                .publishedOn(event.getPublishedOn())
                .location(locationConvertor.toLocationDto(event.getLocation()))
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(event.getInitiator())
                .isPaid(event.getIsPaid())
                .views(event.getViews()) //TODO добавить поля
                .build();
    }

    public Event convertToEvent(EventDto eventDto) {
        return Event.builder()
                .title(eventDto.getTitle())
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .publishedOn(LocalDateTime.now())
                .category(eventDto.getCategory())
                .location(locationConvertor.toLocation(eventDto.getLocation()))
                .isPaid(eventDto.getIsPaid())
                .build();
    }
}
