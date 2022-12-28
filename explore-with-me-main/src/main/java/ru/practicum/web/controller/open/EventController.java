package ru.practicum.web.controller.open;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.service.EventService;
import ru.practicum.web.client.EventStatClient;
import ru.practicum.web.dto.endpointhit.EndpointHit;
import ru.practicum.web.dto.event.EventDto;
import ru.practicum.web.dto.event.EventDtoInCollection;
import ru.practicum.web.dto.event.convertor.EventDtoConvertor;
import ru.practicum.web.dto.event.convertor.EventToEventDtoInCollectionConvertor;

import javax.servlet.http.HttpServletRequest;
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
    private final EventDtoConvertor eventDtoConvertor;
    private final EventStatClient eventStatClient;

    private final String APP_NAME = "explore_with_me";
    private final String EMPTY_STRING = "";
    private final Boolean DEFAULT_ONLY_AVAILABLE = false;
    private final String DEFAULT_SORT = "WITHOUT_SORT";
    private final Integer DEFAULT_FROM = 0;
    private final Integer DEFAULT_SIZE = 10;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<EventDtoInCollection> getEvents(@RequestParam Optional<String> text, @RequestParam Optional<List<Long>> categories,
                                         @RequestParam(name = "paid", required = false) Boolean isPaid,
                                         @RequestParam Optional<LocalDateTime> rangeStart,
                                         @RequestParam Optional<LocalDateTime> rangeEnd,
                                         @RequestParam(name = "onlyAvailable") Optional<Boolean> isOnlyAvailable,
                                         @RequestParam Optional<String> sort,
                                         @RequestParam Optional<Integer> from, @RequestParam Optional<Integer> size,
                                         HttpServletRequest request) {
//        EndpointHit endpointHit = new EndpointHit(APP_NAME, request.getRequestURI(), request.getRemoteAddr(),
//                LocalDateTime.now());
//       // eventStatClient.sendHit(endpointHit);
        return eventService.findAllByParams(text.orElse(EMPTY_STRING), categories.orElse(List.of()), isPaid,
                            rangeStart.orElse(LocalDateTime.now()), rangeEnd.orElse(LocalDateTime.MAX),
                            isOnlyAvailable.orElse(DEFAULT_ONLY_AVAILABLE), sort.orElse(DEFAULT_SORT),
                            from.orElse(DEFAULT_FROM), size.orElse(DEFAULT_SIZE)).stream()
                    .map(eventToEventDtoInCollectionConvertor::convert)
                    .collect(Collectors.toList());
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventDto getEventById(@RequestParam Long eventId, HttpServletRequest request) {
//        EndpointHit endpointHit = new EndpointHit(APP_NAME, request.getRequestURI(), request.getRemoteAddr(),
//                LocalDateTime.now());
//        eventStatClient.sendHit(endpointHit);
        return eventDtoConvertor.convertToDto(eventService.findById(eventId));
    }
}
