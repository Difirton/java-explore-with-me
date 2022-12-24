package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.repository.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {
}