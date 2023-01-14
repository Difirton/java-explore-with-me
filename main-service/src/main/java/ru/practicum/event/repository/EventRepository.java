package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.event.repository.entity.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {

    Optional<Event> findByIdAndInitiatorId(Long id, Long initiatorId);

    List<Event> findEventsByInitiatorId(Long userId, PageRequest pageRequest);

    Optional<Event> findEventByIdAndInitiatorId(Long eventId, Long userId);

    @Query(value = "SELECT * FROM events AS e " +
            "LEFT JOIN likes AS l on e.id = l.event_id " +
            "WHERE l.reviewer_id <> ?1 AND e.initiator_id <> ?1 " +
            "ORDER BY e.likes_rating DESC ",
            countQuery = "SELECT count(*) FROM events AS e " +
                    "INNER JOIN likes AS l on e.id = l.event_id " +
                    "WHERE l.reviewer_id <> ?1 AND e.initiator_id <> ?1 ",
            nativeQuery = true)
    Page<Event> findPopularEventWithoutLikesEventsOfUsers(Long userId, PageRequest pageRequest);
}