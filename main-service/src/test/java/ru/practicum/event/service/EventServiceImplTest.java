package ru.practicum.event.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.ActiveProfiles;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;

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
                .createdOn(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .publishedOn(LocalDateTime.of(2000, 2, 2, 2, 2, 2))
                .eventDate(LocalDateTime.of(2000, 3, 3, 3, 3, 3))
                .isPaid(true)
                .requestModeration(true)
                .views(4)
                .build();
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
}