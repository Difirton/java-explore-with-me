package ru.practicum.request.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.request.constant.Status;
import ru.practicum.request.repository.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.repository.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class RequestServiceImplTest {
    private User user;
    private Request request;
    private Event event;
    @Autowired
    private RequestService requestService;
    @MockBean
    private EventRepository mockEventRepository;
    @MockBean
    private UserRepository mockUserRepository;
    @MockBean
    private RequestRepository mockRepository;

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
                .annotation("Test annot")
                .participantLimit(5)
                .confirmedRequests(3)
                .state(State.PUBLISHED)
                .requestModeration(false)
                .initiator(User.builder()
                        .id(2L)
                        .build())
                .build();
        request = Request.builder()
                .id(1L)
                .created(LocalDateTime.of(2000, 10, 5, 4, 3, 2))
                .requester(user)
                .event(event)
                .status(Status.PENDING)
                .build();
        when(mockRepository.findById(1L)).thenReturn(Optional.of(request));
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mockEventRepository.findById(2L)).thenReturn(Optional.of(event));
        when(mockRepository.findRequestByRequesterAndEvent(user, event)).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("Test create new request")
    void testCreateRequest() {
        requestService.createRequest(1L, 2L);
        verify(mockRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Test find all user requests")
    void testFindAllUserRequests() {
        requestService.findAllUserRequests(1L);
        verify(mockRepository, times(1)).findRequestsByRequesterId(1L);
    }

    @Test
    @DisplayName("Test cancel request")
    void testCancelRequest() {

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