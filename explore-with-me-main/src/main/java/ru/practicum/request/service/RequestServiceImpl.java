package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.error.EventNotFoundException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.request.constant.Status;
import ru.practicum.request.error.RequestNotFoundException;
import ru.practicum.request.error.RequestsLimitException;
import ru.practicum.request.repository.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.error.UserNotFoundException;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.repository.entity.User;
import ru.practicum.web.user.UserRequestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    @Autowired
    private final RequestRepository requestRepository;
    @Autowired
    private final EventRepository eventRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public Request createRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        this.checkRequestParameters(event, user);

        Request request = new Request();
        request.setEvent(event);
        request.setRequester(user);

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(Status.CONFIRMED);
        } else {
            request.setStatus(Status.PENDING);
        }
        if (event.getParticipantLimit() != 0) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        return requestRepository.save(request);
    }

    private void checkRequestParameters(Event event, User user) {
        if (requestRepository.findRequestByRequesterAndEvent(user, event).isPresent()) {
            throw new IllegalStateException("Request already exist");
        }
        if (event.getInitiator().getId().equals(user.getId())) {
            throw new IllegalStateException("Requester is initiator of event");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new IllegalStateException("Event is not published");
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new IllegalStateException("The limit of applications has been reached, application with id has been rejected")
        }
    }

    @Override
    public List<Request> findAllRequests(Long userId) {
        return requestRepository.findRequestsByRequesterId(userId);
    }

    @Override
    public Request cancelRequest(Long userId, Long requestId) {
        Request request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new RequestNotFoundException(requestId, userId));
        request.setStatus(Status.CANCELED);
        requestRepository.save(request);
        return request;
    }




    @Override
    public Request getRequests(Long userId, Long eventId) {
        return requestRepository.findRequestByRequesterAndEvent(userId, eventId);
    }

    @Transactional
    @Override
    public Request confirmRequest(Long userId, Long eventId, Long requestId) {
        Event event = eventRepository.findEventByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new EventNotFoundException(eventId, userId));
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            return request;
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new RequestsLimitException(requestId);
        }
        if (event.getParticipantLimit() == (event.getConfirmedRequests() + 1)) {
            List<Request> pendingRequests = requestRepository.findRequestsByEventAndStatus(event, Status.PENDING);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            request.setStatus(Sta);
            pendingRequests.forEach(req -> req.setStatus(Status.CANCELED));
        } else {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            request.setStatus(Status.CONFIRMED);
        }

        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public Request rejectRequest(Long userId, Long eventId, Long requestId) {
        return null;
    }
}
