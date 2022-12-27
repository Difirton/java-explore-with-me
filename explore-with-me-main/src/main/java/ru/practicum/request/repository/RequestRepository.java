package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.user.repository.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findRequestByRequesterAndEvent(User user, Event event);

    Optional<Request> findRequestByIdAndRequesterId(long requestId, long userId);

    List<Request> findRequestsByEvent(Event event);

    List<Request> findRequestsByEventAndState(Event event, State state);

    List<Request> findRequestsByRequesterId(long userId);
}
