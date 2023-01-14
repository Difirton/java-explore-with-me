package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.common.NotNullPropertiesCopier;
import ru.practicum.event.error.EventNotFoundException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.LocationRepository;
import ru.practicum.event.repository.constant.EventSort;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.event.repository.entity.Location;
import ru.practicum.user.error.UserNotFoundException;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.repository.entity.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.practicum.event.repository.constant.EventSort.*;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService, NotNullPropertiesCopier<Event> {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    private static final Integer ONE_HOUR = 1;
    private static final Integer TWO_HOUR = 2;

    @Override
    public List<Event> findAllByParams(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Boolean isOnlyAvailable, String sort,
                                       Integer from, Integer size) {
        if (size < 1 || from < 0) {
            throw new IllegalStateException("Incorrect pagination request");
        }
        switch (this.checkInputEventSortData(sort)) {
            case WITHOUT_SORT:
                return eventRepository.findAllByParams(text, categoriesIds, isPaid, rangeStart, rangeEnd,
                        isOnlyAvailable, from, size);
            case EVENT_DATE:
                return eventRepository.findAllByParamsEventDateSort(text, categoriesIds, isPaid, rangeStart, rangeEnd,
                        isOnlyAvailable, from, size);
            case VIEWS:
                return eventRepository.findAllByParamsViewsSort(text, categoriesIds, isPaid, rangeStart, rangeEnd,
                        isOnlyAvailable, from, size);
            default:
                throw new IllegalStateException("Unknown sort: " + sort);
        }
    }

    @Override
    public List<Event> findAllByParams(List<Long> users, List<State> states, List<Long> categories, LocalDateTime start,
                                       LocalDateTime finish, Integer from, Integer size) {
        return eventRepository.findAllByParams(users, states, categories, start, finish, from, size);
    }

    private EventSort checkInputEventSortData(String eventSortName) {
        EventSort eventSort = UNSUPPORTED;
        if (Arrays.stream(EventSort.values()).anyMatch(s -> s.toString().equals(eventSortName))) {
            eventSort = EventSort.valueOf(eventSortName);
        }
        return eventSort;
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @Override
    public Event findByUserIdAndId(Long userId, Long eventId) {
        return eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }

    @Override
    public List<Event> findEventsByUser(Long userId, Integer from, Integer size) {
        return eventRepository.findEventsByInitiatorId(userId, PageRequest.of(from, size));
    }

    @Override
    public Event createEvent(Long userId, Event event) {
        this.checkTimeEvent(event.getEventDate(), TWO_HOUR);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        event.setInitiator(user);
        event.setState(State.PENDING);
        this.saveLocation(event.getLocation());
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long userId, Event event) {
        Event eventToUpdate = findByUserIdAndId(userId, event.getId());
        if (eventToUpdate.getState().equals(State.PUBLISHED)) {
            throw new IllegalArgumentException("Event is published");
        }
        this.checkTimeEvent(event.getEventDate(), TWO_HOUR);
        if (eventToUpdate.getEventDate() != null) {
            this.checkTimeEvent(eventToUpdate.getEventDate(), TWO_HOUR);
        }
        copyNotNullProperties(event, eventToUpdate);
        return eventRepository.save(eventToUpdate);
    }

    @Override
    public Event updateEvent(Event event) {
        Event eventToUpdate = findById(event.getId());
        copyNotNullProperties(event, eventToUpdate);
        return eventRepository.save(eventToUpdate);
    }

    @Override
    public Event cancelEvent(Long userId, Long eventId) {
        Event event = this.findByUserIdAndId(userId, eventId);
        if (!event.getState().equals(State.PENDING)) {
            throw new IllegalArgumentException("Event pending");
        }
        event.setState(State.CANCELED);
        return eventRepository.save(event);
    }

    @Override
    public Event publishEvent(Long eventId) {
        Event event = this.findById(eventId);
        this.checkTimeEvent(event.getEventDate(), ONE_HOUR);
        if (event.getState() != State.PENDING) {
            throw new IllegalStateException("Event state must be - waiting");
        }
        event.setState(State.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return eventRepository.save(event);
    }

    @Override
    public Event rejectEvent(Long eventId) {
        Event event = this.findById(eventId);
        if (event.getState() == State.PUBLISHED) {
            throw new IllegalStateException("Event state is published");
        }
        event.setState(State.CANCELED);
        return eventRepository.save(event);
    }

    @Override
    public List<Event> findMostPopular(Integer from, Integer size) {
        return eventRepository.findAll(PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "countLikes")))
                .toList();
    }

    @Override
    public List<Event> findUserRecommendation(Long userId, Integer from, Integer size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return eventRepository.findPopularEventWithoutLikesEventsOfUsers(userId, PageRequest.of(from, size)).stream()
                .distinct()
                .collect(toList());
    }

    private void saveLocation(Location location) {
        if (locationRepository.findLocationByLatAndLon(location.getLat(), location.getLon()).isEmpty()) {
            locationRepository.save(location);
        }
    }

    private void checkTimeEvent(LocalDateTime eventTime, Integer timeCheck) {
        if (eventTime.minusHours(timeCheck).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event can not be earlier than two hours from the current moment");
        }
    }
}