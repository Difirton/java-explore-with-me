package ru.practicum.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService, NotNullPropertiesCopier<Like> {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Like createLike(Long userId, Long eventId, Boolean isLike) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
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
            return likeRepository.save(like);
        }
    }

    @Override
    public Like updateLike(Like like) {
        Like likeToUpdate = likeRepository.findById(like.getId())
                .orElseThrow(() -> new LikeNotFoundException(like.getId()));
        this.copyNotNullProperties(like, likeToUpdate);
        return likeRepository.save(likeToUpdate);
    }

    @Override
    public void deleteLike(Long userId, Long eventId) {
        likeRepository.findByReviewerIdAndEventId(userId, eventId)
                .ifPresent(likeRepository::delete);
    }
}
