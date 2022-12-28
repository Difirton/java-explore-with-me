package ru.practicum.request.service;

import ru.practicum.request.repository.Request;

import java.util.List;

public interface RequestService {
    Request createRequest(Long userId, Long eventId);

    List<Request> findAllRequests(Long userId);

    Request cancelRequest(Long userId, Long requestId);

    List<Request> getRequests(Long userId, Long eventId);

    Request confirmRequest(Long userId, Long eventId, Long requestId);

    Request rejectRequest(Long userId, Long eventId, Long requestId);
}
