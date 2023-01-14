package ru.practicum.event.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.event.repository.entity.Event;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:script/schema_EventRepositoryTest.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("Test recommended collections events search")
    void testFindPopularEventWithoutLikesEventsOfUsers() {
        List<Event> user1Events = eventRepository
                .findPopularEventWithoutLikesEventsOfUsers(1L, PageRequest.of(0, 10))
                .toList();
        assertEquals(1, user1Events.size());
        assertEquals("Test 3", user1Events.get(0).getTitle());
        List<Event> user2Events = eventRepository
                .findPopularEventWithoutLikesEventsOfUsers(2L, PageRequest.of(0, 10))
                .toList();
        assertEquals(0, user2Events.size());
        List<Event> user3Events = eventRepository
                .findPopularEventWithoutLikesEventsOfUsers(3L, PageRequest.of(0, 10))
                .toList();
        assertEquals(2, user3Events.size());
        assertEquals("Test 1", user3Events.get(0).getTitle());
        assertEquals("Test 2", user3Events.get(1).getTitle());
        List<Event> user4Events = eventRepository
                .findPopularEventWithoutLikesEventsOfUsers(4L, PageRequest.of(0, 10))
                .toList();
        assertEquals(3, user4Events.size());
        assertEquals("Test 1", user4Events.get(0).getTitle());
        assertEquals("Test 3", user4Events.get(1).getTitle());
        assertEquals("Test 2", user4Events.get(1).getTitle());
    }

    @Test
    @DisplayName("Test size page recommended collections events search")
    void testPagingFindPopularEventWithoutLikesEventsOfUsers() {
        List<Event> user4Events1InPage = eventRepository
                .findPopularEventWithoutLikesEventsOfUsers(4L, PageRequest.of(1, 1))
                .toList();
        assertEquals(1, user4Events1InPage.size());
        assertEquals("Test 3", user4Events1InPage.get(0).getTitle());
        List<Event> user4Events2InPage = eventRepository
                .findPopularEventWithoutLikesEventsOfUsers(4L, PageRequest.of(0, 2))
                .toList();
        assertEquals(2, user4Events2InPage.size());
        assertEquals("Test 1", user4Events2InPage.get(0).getTitle());
        assertEquals("Test 3", user4Events2InPage.get(1).getTitle());
    }
}