package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.repository.entity.EndpointHit;
import ru.practicum.web.dto.StatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT new ru.practicum.web.dto.StatDto(e.app, e.uri, COUNT(e.id))  " +
            "FROM EndpointHit AS e " +
            "WHERE e.uri IN ?1 " +
          //  "AND e.timestamp BETWEEN ?2 AND ?3 " +
            "GROUP BY e.app, e.uri")
    List<StatDto> findStats(List<String> uris, LocalDateTime start, LocalDateTime finish);

    @Query("SELECT new ru.practicum.web.dto.StatDto(e.app, e.uri , COUNT(DISTINCT e.ip)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.uri IN ?1 " +
            //"AND e.timestamp BETWEEN ?2 AND ?3 " +
            "GROUP BY e.app, e.uri, e.ip")
    List<StatDto> findUniqueStats(List<String> uris, LocalDateTime finish, LocalDateTime end);
}
