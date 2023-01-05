package ru.practicum.request.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.request.repository.Request;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.repository.entity.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
class RequestServiceImplTest {
    private User user;
    private Request request;
    private Event event;
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository mockRepository;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .name("Test")
                .build();
        event = Event.builder()
                .id(2L)
                .title("test")
                .description("test")
                .confirmedRequests(2)
                .initiator(user)
                .annotation("Test annot")
                .build();
        request = Request.builder()
                .created(LocalDateTime.of(2000, 10, 5, 4, 3, 2))
                .requester(user)
                .build();
    }

    @Test
    void createRequest() {
    }

    @Test
    void findAllUserRequests() {
    }

    @Test
    void cancelRequest() {
    }

    @Test
    void findRequestsByUserIdAndEventId() {
    }

    @Test
    void confirmRequest() {
    }

    @Test
    void rejectRequest() {
    }
}