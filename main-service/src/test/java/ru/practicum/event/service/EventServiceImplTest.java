package ru.practicum.event.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.event.repository.entity.Location;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.repository.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class EventServiceImplTest {
    private Event event;

    @Autowired
    private EventService eventService;
    @MockBean
    private UserRepository mockUserRepository;
    @MockBean
    private EventRepository mockRepository;

    @BeforeEach
    void setUp() {
        event = Event.builder()
                .id(1L)
                .category(Category.builder()
                        .id(1L)
                        .build())
                .title("Test")
                .annotation("test anot")
                .description("test desc")
                .createdOn(LocalDateTime.of(2030, 1, 1, 1, 1, 1))
                .publishedOn(LocalDateTime.of(2030, 2, 2, 2, 2, 2))
                .eventDate(LocalDateTime.of(2030, 3, 3, 3, 3, 3))
                .isPaid(true)
                .location(Location.builder()
                        .lat(BigDecimal.ONE)
                        .lon(BigDecimal.TEN)
                        .build())
                .requestModeration(true)
                .views(4)
                .build();
        when(mockUserRepository.findById(1L))
                .thenReturn(Optional.of(User.builder()
                        .id(1L)
                        .build()));
    }

    @Test
    @DisplayName("Test find event by many params without sort")
    void testFindAllByParams1() {
        eventService.findAllByParams("test", List.of(1L), true, LocalDateTime.MIN, LocalDateTime.MAX,
                true, "WITHOUT_SORT", 0, 5);
        verify(mockRepository, times(1)).findAllByParams("test", List.of(1L),
                true, LocalDateTime.MIN, LocalDateTime.MAX, true, 0, 5);
    }

    @Test
    @DisplayName("Test find event by many params with sort by date")
    void testFindAllByParams2() {
        eventService.findAllByParams("test", List.of(1L), true, LocalDateTime.MIN, LocalDateTime.MAX,
                true, "EVENT_DATE", 0, 5);
        verify(mockRepository, times(1)).findAllByParamsEventDateSort("test", List.of(1L),
                true, LocalDateTime.MIN, LocalDateTime.MAX, true, 0, 5);
    }

    @Test
    @DisplayName("Test find event by many params with sort by view")
    void testFindAllByParam3() {
        eventService.findAllByParams("test", List.of(1L), true, LocalDateTime.MIN, LocalDateTime.MAX,
                true, "VIEWS", 0, 5);
        verify(mockRepository, times(1)).findAllByParamsViewsSort("test", List.of(1L),
                true, LocalDateTime.MIN, LocalDateTime.MAX, true, 0, 5);
    }

    @Test
    @DisplayName("Test unknown sort")
    void testFindAllByParam4() {
        assertThrows(IllegalStateException.class, () -> eventService.findAllByParams("test", List.of(1L),
                true, LocalDateTime.MIN, LocalDateTime.MAX, true, "", 0, 5));
    }

    @Test
    @DisplayName("Test find event by many params with by users ids")
    void testFindAllByParam5() {
        eventService.findAllByParams(List.of(1L), List.of(State.PENDING), List.of(1L), LocalDateTime.MIN,
                LocalDateTime.MAX, 0, 5);
        verify(mockRepository, times(1)).findAllByParams(List.of(1L), List.of(State.PENDING),
                List.of(1L), LocalDateTime.MIN, LocalDateTime.MAX, 0, 5);
    }

    @Test
    @DisplayName("Test find event by id")
    void testFindById() {
        when(mockRepository.findById(1L)).thenReturn(Optional.of(event));
        eventService.findById(1L);
        verify(mockRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test find event by id and user id")
    void testFindByUserIdAndId() {
        when(mockRepository.findByIdAndInitiatorId(1L, 1L)).thenReturn(Optional.of(event));
        eventService.findByUserIdAndId(1L, 1L);
        verify(mockRepository, times(1)).findByIdAndInitiatorId(1L, 1L);
    }

    @Test
    @DisplayName("Test find events by user id")
    void testFindsEventsByUser() {
        when(mockRepository.findEventsByInitiatorId(1L, PageRequest.of(0, 5))).thenReturn(List.of(event));
        eventService.findEventsByUser(1L, 0, 5);
        verify(mockRepository, times(1))
                .findEventsByInitiatorId(1L, PageRequest.of(0, 5));
    }

    @Test
    @DisplayName("Test create event by user id")
    void createEvent() {
        when(mockRepository.save(event)).thenReturn(event);
        eventService.createEvent(1L, event);
        verify(mockRepository, times(1)).save(event);
    }

    @Test
    @DisplayName("Test update event")
    void updateEvent() {
        when(mockRepository.findByIdAndInitiatorId(1L, 1L)).thenReturn(Optional.of(event));
        Event updatedEvent = Event.builder()
                .id(1L)
                .title("TEST 2")
                .eventDate(LocalDateTime.of(2030, 3, 3, 3, 3, 3))
                .build();
        when(mockRepository.save(event)).thenReturn(event);
        when(mockRepository.findByIdAndInitiatorId(1L, 1L)).thenReturn(Optional.of(event));
        eventService.updateEvent(1L, updatedEvent);
        verify(mockRepository, times(1)).save(event);
    }

    @Test
    @DisplayName("Test find recommendation for user")
    void findUserRecommendation() {
        when(mockRepository.findPopularEventWithoutLikesEventsOfUsers(1L, PageRequest.of(0, 5)))
                .thenReturn(Page.empty());
        eventService.findUserRecommendation(1L, 0, 5);
        verify(mockRepository, times(1))
                .findPopularEventWithoutLikesEventsOfUsers(1L, PageRequest.of(0, 5));
    }
}