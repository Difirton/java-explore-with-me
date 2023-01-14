package ru.practicum.like.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.event.repository.entity.Location;
import ru.practicum.like.repository.LikeRepository;
import ru.practicum.like.repository.entity.Like;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.repository.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class LikeServiceImplTest {
    private Like like;
    private Event event;

    @Autowired
    private LikeService likeService;
    @MockBean
    private LikeRepository mockRepository;
    @MockBean
    private EventRepository mockEventRepository;
    @MockBean
    private UserRepository mockUserRepository;

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
                .likesRating(3)
                .views(4)
                .build();
        when(mockEventRepository.findById(1L))
                .thenReturn(Optional.of(event));
        when(mockUserRepository.findById(1L))
                .thenReturn(Optional.of(User.builder()
                        .id(1L)
                        .build()));
    }

    @Test
    @DisplayName("Test create positive like")
    void testCreateLike1() {
        likeService.createLike(1L, 1L, true);
        Assertions.assertEquals(4, event.getLikesRating());
        verify(mockUserRepository, times(1)).findById(1L);
        verify(mockEventRepository, times(1)).findById(1L);
        verify(mockEventRepository, times(1)).save(any());
        verify(mockRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Test create negative like")
    void testCreateLike2() {
        likeService.createLike(1L, 1L, false);
        Assertions.assertEquals(2, event.getLikesRating());
        verify(mockUserRepository, times(1)).findById(1L);
        verify(mockEventRepository, times(1)).findById(1L);
        verify(mockEventRepository, times(1)).save(any());
        verify(mockRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Test update positive like to negative like")
    void testUpdateLike1() {
        when(mockRepository.findByReviewerIdAndEventId(1L, 1L))
                .thenReturn(Optional.of(Like.builder()
                        .event(event)
                        .reviewer(User.builder()
                                .id(1L)
                                .build())
                        .isLike(true)
                        .build()));
        likeService.updateLike(1L, 1L, false);
        Assertions.assertEquals(1, event.getLikesRating());
        verify(mockRepository, times(1)).findByReviewerIdAndEventId(1L, 1L);
        verify(mockRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Test update negative like to positive like")
    void testUpdateLike2() {
        when(mockRepository.findByReviewerIdAndEventId(1L, 1L))
                .thenReturn(Optional.of(Like.builder()
                        .event(event)
                        .reviewer(User.builder()
                                .id(1L)
                                .build())
                        .isLike(false)
                        .build()));
        likeService.updateLike(1L, 1L, true);
        Assertions.assertEquals(5, event.getLikesRating());
        verify(mockRepository, times(1)).findByReviewerIdAndEventId(1L, 1L);
        verify(mockRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Test update positive like to positive like")
    void testUpdateLike3() {
        when(mockRepository.findByReviewerIdAndEventId(1L, 1L))
                .thenReturn(Optional.of(Like.builder()
                        .event(event)
                        .reviewer(User.builder()
                                .id(1L)
                                .build())
                        .isLike(true)
                        .build()));
        likeService.updateLike(1L, 1L, true);
        Assertions.assertEquals(3, event.getLikesRating());
        verify(mockRepository, times(1)).findByReviewerIdAndEventId(1L, 1L);
        verify(mockRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Test delete positive like")
    void testDeleteLike1() {
        when(mockRepository.findByReviewerIdAndEventId(1L, 1L))
                .thenReturn(Optional.of(Like.builder()
                        .event(event)
                        .reviewer(User.builder()
                                .id(1L)
                                .build())
                        .isLike(true)
                        .build()));
        likeService.deleteLike(1L, 1L);
        Assertions.assertEquals(2, event.getLikesRating());
        verify(mockEventRepository, times(1)).findById(1L);
        verify(mockRepository, times(1)).findByReviewerIdAndEventId(1L, 1L);
        verify(mockEventRepository, times(1)).save(any());
        verify(mockRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Test delete negative like")
    void testDeleteLike2() {
        when(mockRepository.findByReviewerIdAndEventId(1L, 1L))
                .thenReturn(Optional.of(Like.builder()
                        .event(event)
                        .reviewer(User.builder()
                                .id(1L)
                                .build())
                        .isLike(false)
                        .build()));
        likeService.deleteLike(1L, 1L);
        Assertions.assertEquals(4, event.getLikesRating());
        verify(mockEventRepository, times(1)).findById(1L);
        verify(mockRepository, times(1)).findByReviewerIdAndEventId(1L, 1L);
        verify(mockEventRepository, times(1)).save(any());
        verify(mockRepository, times(1)).delete(any());
    }
}