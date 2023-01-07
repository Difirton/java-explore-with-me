package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.request.constant.Status;
import ru.practicum.user.repository.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findRequestByRequesterAndEvent(User user, Event event);

    Optional<Request> findByIdAndRequesterId(Long requestId, Long userId);

    List<Request> findRequestsByEvent(Event event);

    List<Request> findRequestsByEventAndStatus(Event event, Status status);

    List<Request> findRequestsByRequesterId(Long userId);
}
