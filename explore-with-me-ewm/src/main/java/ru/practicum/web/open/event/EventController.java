package ru.practicum.web.open.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.service.EventService;
import ru.practicum.web.open.event.convertor.EventDtoCreator;
import ru.practicum.web.open.event.convertor.EventToEventDtoInCollectionConvertor;
import ru.practicum.web.open.event.dto.EventDto;
import ru.practicum.web.open.event.dto.EventDtoInCollection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {
    private final EventService eventService;
    private final EventToEventDtoInCollectionConvertor eventToEventDtoInCollectionConvertor;
    private final EventDtoCreator eventDtoCreator;

    private final String EMPTY_STRING = "";
    private final Boolean DEFAULT_ONLY_AVAILABLE;
    private final String DEFAULT_SORT = "ID";
    private final Integer DEFAULT_FROM = 10;
    private final Integer DEFAULT_SIZE = 0;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<EventDtoInCollection> getEvents(@RequestParam Optional<String> text, @RequestParam Optional<List<Long>> categories,
                                         @RequestParam(name = "paid") Optional<Boolean> isPaid,
                                         @RequestParam Optional<LocalDateTime> rangeStart,
                                         @RequestParam Optional<LocalDateTime> rangeEnd,
                                         @RequestParam(name = "onlyAvailable") Optional<Boolean> isOnlyAvailable,
                                         @RequestParam Optional<String> sort,
                                         @RequestParam Optional<Integer> from, @RequestParam Optional<Integer> size) {
        //TODO добавить отправку и получение статистики
        if (isPaid.isPresent()) {
            return eventService.findAll(text.orElse(EMPTY_STRING), categories.orElse(List.of()), isPaid.get(),
                            rangeStart.orElse(LocalDateTime.now()), rangeEnd.orElse(LocalDateTime.MAX),
                            isOnlyAvailable.orElse(DEFAULT_ONLY_AVAILABLE), sort.orElse(DEFAULT_SORT),
                            from.orElse(DEFAULT_FROM), size.orElse(DEFAULT_SIZE)).stream()
                    .map(eventToEventDtoInCollectionConvertor::convert)
                    .collect(Collectors.toList());
        } else {
            return eventService.findAll(text.orElse(EMPTY_STRING), categories.orElse(List.of()),
                            rangeStart.orElse(LocalDateTime.now()), rangeEnd.orElse(LocalDateTime.MAX),
                            isOnlyAvailable.orElse(DEFAULT_ONLY_AVAILABLE), sort.orElse(DEFAULT_SORT),
                            from.orElse(DEFAULT_FROM), size.orElse(DEFAULT_SIZE)).stream()
                    .map(eventToEventDtoInCollectionConvertor::convert)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    EventDto getEventById(@RequestParam Long id) {
        return eventDtoCreator.create(eventService.findById(id));
    }
}
