package ru.practicum.web.controller.open;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.service.EventService;
import ru.practicum.utill.DateTimeParser;
import ru.practicum.web.client.EventStatClient;
import ru.practicum.web.dto.endpointhit.HitDto;
import ru.practicum.web.dto.event.EventDto;
import ru.practicum.web.dto.event.EventDtoInCollection;
import ru.practicum.web.dto.event.convertor.EventDtoConvertor;
import ru.practicum.web.dto.event.convertor.EventToEventDtoInCollectionConvertor;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {
    private final EventService eventService;
    private final EventToEventDtoInCollectionConvertor eventToEventDtoInCollectionConvertor;
    private final EventDtoConvertor eventDtoConvertor;
    private final EventStatClient eventStatClient;

    private static final String APP_NAME = "ewm-main-service";
    private static final String EMPTY_STRING = "";
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 1, 1);
    private static final Boolean DEFAULT_ONLY_AVAILABLE = false;
    private static final String DEFAULT_SORT = "WITHOUT_SORT";
    private static final Integer DEFAULT_FROM = 0;
    private static final Integer DEFAULT_SIZE = 10;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<EventDtoInCollection> getEvents(@RequestParam Optional<String> text, @RequestParam Optional<List<Long>> categories,
                                         @RequestParam(name = "paid", required = false) Boolean isPaid,
                                         @RequestParam Optional<String> rangeStart,
                                         @RequestParam Optional<String> rangeEnd,
                                         @RequestParam(name = "onlyAvailable") Optional<Boolean> isOnlyAvailable,
                                         @RequestParam Optional<String> sort,
                                         @RequestParam Optional<Integer> from, @RequestParam Optional<Integer> size,
                                         HttpServletRequest request) {
        HitDto hitDto = new HitDto(null, APP_NAME, request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now());
        eventStatClient.sendHit(hitDto);
        return eventService.findAllByParams(text.orElse(EMPTY_STRING), categories.orElse(List.of()), isPaid,
                        rangeStart.map(DateTimeParser::parseToLocalDateTime).orElse(LocalDateTime.now()),
                        rangeEnd.map(DateTimeParser::parseToLocalDateTime).orElse(MAX_DATE),
                        isOnlyAvailable.orElse(DEFAULT_ONLY_AVAILABLE), sort.orElse(DEFAULT_SORT),
                        from.orElse(DEFAULT_FROM), size.orElse(DEFAULT_SIZE)).stream()
                    .map(eventToEventDtoInCollectionConvertor::convert)
                    .collect(Collectors.toList());
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventDto getEventById(@Valid @PathVariable Long eventId, HttpServletRequest request) {
        HitDto hitDto = new HitDto(null, APP_NAME, request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now());
        eventStatClient.sendHit(hitDto);
        return eventDtoConvertor.convertToDto(eventService.findById(eventId));
    }

    @GetMapping("/most-popular")
    List<EventDtoInCollection> getEvents(@Positive @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size) {
        return eventService.findMostPopular(from, size).stream()
                .map(eventToEventDtoInCollectionConvertor::convert)
                .collect(toList());
    }
}
