package ru.practicum.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.NotNullPropertiesCopier;
import ru.practicum.event.error.EventNotFoundException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.like.error.LikeNotFoundException;
import ru.practicum.like.repository.LikeRepository;
import ru.practicum.like.repository.entity.Like;
import ru.practicum.user.error.UserNotFoundException;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.repository.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService, NotNullPropertiesCopier<Like> {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Like createLike(Long userId, Long eventId, Boolean isLike) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (isLike) {
            event.setLikesRating(event.getLikesRating() + 1);
        } else {
            event.setLikesRating(event.getLikesRating() - 1);
        }
        eventRepository.save(event);
        return likeRepository.save(Like.builder()
                .reviewer(user)
                .event(event)
                .isLike(isLike)
                .build());
    }

    @Override
    public Like updateLike(Long userId, Long eventId, Boolean isLike) {
        Like like = likeRepository.findByReviewerIdAndEventId(userId, eventId)
                .orElseThrow(() -> new LikeNotFoundException(userId, eventId));
        if (isLike.equals(like.getIsLike())) {
            return like;
        } else {
            like.setIsLike(isLike);
            this.changeEventRatingAfterUpdate(eventId, isLike);
            return likeRepository.save(like);
        }
    }

    @Override
    public void deleteLike(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        likeRepository.findByReviewerIdAndEventId(userId, eventId)
                .ifPresent(like -> {
                    if (like.getIsLike()) {
                        event.setLikesRating(event.getLikesRating() - 1);
                    } else {
                        event.setLikesRating(event.getLikesRating() + 1);
                    }
                    likeRepository.delete(like);
                    eventRepository.save(event);
                });
    }

    private void changeEventRatingAfterUpdate(Long eventId, Boolean isLike) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        if (isLike) {
            event.setLikesRating(event.getLikesRating() + 2);
        } else {
            event.setLikesRating(event.getLikesRating() - 2);
        }
        eventRepository.save(event);
    }
}
