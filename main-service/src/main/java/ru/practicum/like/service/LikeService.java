package ru.practicum.like.service;

import ru.practicum.like.repository.entity.Like;

public interface LikeService {

    Like createLike(Long userId, Long eventId, Boolean isLike);

    Like updateLike(Long userId, Long eventId, Boolean isLike);

    Like updateLike(Like like);

    void deleteLike(Long userId, Long eventId);
}
