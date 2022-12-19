package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.repository.entity.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    List<Hit> findAllByTimestampBetween(LocalDateTime start, LocalDateTime finish);

    List<Hit> findAllByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime finish, List<String> uris);

    @Query("SELECT h " +
            " FROM hit as h " +
            " WHERE s.uri IN :uris AND s.timestamp BETWEEN :start AND :end " +
            " GROUP BY s.app, s.uri, s.ip ")
    List<Hit> countByTimestampAndListUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
