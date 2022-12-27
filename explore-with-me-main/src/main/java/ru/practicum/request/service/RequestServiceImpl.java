package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.request.repository.Request;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    @Override
    public Request addRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public List<Request> findAllRequests(Long userId) {
        return null;
    }

    @Override
    public Request cancelRequest(Long userId, Long requestId) {
        return null;
    }

    @Override
    public List<Request> getRequests(Long userId, Long eventId) {
        return null;
    }

    @Override
    public Request confirmRequest(Long userId, Long eventId, Long requestId) {
        return null;
    }

    @Override
    public Request rejectRequest(Long userId, Long eventId, Long requestId) {
        return null;
    }
}
