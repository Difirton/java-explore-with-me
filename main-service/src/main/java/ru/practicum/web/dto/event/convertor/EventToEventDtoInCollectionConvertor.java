package ru.practicum.web.dto.event.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.web.dto.event.EventDtoInCollection;

@Component
public class EventToEventDtoInCollectionConvertor implements Converter<Event, EventDtoInCollection> {
    @Override
    public EventDtoInCollection convert(Event source) {
        return EventDtoInCollection.builder()
                .id(source.getId())
                .title(source.getTitle())
                .annotation(source.getAnnotation())
                .category(source.getCategory())
                .eventDate(source.getEventDate())
                .confirmedRequests(source.getConfirmedRequests())
                .initiator(source.getInitiator())
                .paid(source.getIsPaid())
                .views(source.getViews())
                .build();
    }
}
