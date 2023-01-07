package ru.practicum.web.dto.event.convertor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.event.repository.entity.Location;
import ru.practicum.web.dto.category.convertor.CategoryDtoToCategoryConvertor;
import ru.practicum.web.dto.category.convertor.CategoryToCategoryDtoConvertor;
import ru.practicum.web.dto.event.EventDto;
import ru.practicum.web.dto.event.EventInDto;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventDtoConvertor {
    private final LocationConvertor locationConvertor;
    private final CategoryToCategoryDtoConvertor categoryToCategoryDtoConvertor;
    private final CategoryDtoToCategoryConvertor categoryDtoToCategoryConvertor;

    public EventDto convertToDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(categoryToCategoryDtoConvertor.convert(event.getCategory()))
                .eventDate(event.getEventDate())
                .publishedOn(event.getPublishedOn())
                .location(locationConvertor.toLocationDto(event.getLocation()))
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(event.getInitiator())
                .requestModeration(event.getRequestModeration())
                .participantLimit(event.getParticipantLimit())
                .paid(event.getIsPaid())
                .state(event.getState())
                .views(event.getViews())
                .build();
    }

    public Event convertToEvent(EventDto eventDto) {
        return Event.builder()
                .id(eventDto.getId())
                .title(eventDto.getTitle())
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .publishedOn(LocalDateTime.now())
                .requestModeration(eventDto.getRequestModeration())
                .category(categoryDtoToCategoryConvertor.convert(eventDto.getCategory()))
                .location(locationConvertor.toLocation(eventDto.getLocation()))
                .isPaid(eventDto.getPaid())
                .build();
    }

    public Event convertToEvent(EventInDto eventDto) {
        Location location = null;
        Category category = null;
        if (eventDto.getLocation() != null) {
            location = locationConvertor.toLocation(eventDto.getLocation());
        }
        if (eventDto.getCategory() != null) {
            category = Category.builder()
                    .id(eventDto.getCategory())
                    .build();
        }
        return Event.builder()
                .id(eventDto.getId())
                .title(eventDto.getTitle())
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .publishedOn(LocalDateTime.now())
                .requestModeration(eventDto.getRequestModeration())
                .participantLimit(eventDto.getParticipantLimit())
                .category(category)
                .location(location)
                .isPaid(eventDto.getPaid())
                .build();
    }
}
