package ru.practicum.web.open.event.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.web.open.event.dto.EventDtoInCollection;

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
                .paid(source.getPaid())
                .views(source.getViews()) //TODO подумать рнужны ли тут вьюхи
                .build();
    }
}
