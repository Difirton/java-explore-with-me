package ru.practicum.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.repository.StatRepository;
import ru.practicum.repository.entity.EndpointHit;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class StatServiceImplTest {
    private EndpointHit endpointHit;
    @Autowired
    private StatService statService;
    @MockBean
    private StatRepository mockRepository;

    @BeforeEach
    void setUp() {
        endpointHit = EndpointHit.builder()
                .ip("200.200.200.200")
                .uri("/test1/test2")
                .app("Test")
                .build();
    }

    @Test
    @DisplayName("Create new stat endpointHit, expected OK")
    void testSave() {
        List<EndpointHit> hits = new ArrayList<>();
        hits.add(EndpointHit.builder()
                .ip("200.200.200.200")
                .uri("/test1")
                .app("Test")
                .build());
        hits.add(EndpointHit.builder()
                .ip("200.200.200.200")
                .uri("/test1/test2")
                .app("Test")
                .build());
        statService.save(endpointHit);
        verify(mockRepository, times(1)).saveAll(hits);
    }

    @Test
    @DisplayName("Test get stats expected OK")
    void testGetStats() {
        statService.getStats(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(1), List.of("test"), false);
        verify(mockRepository, times(1)).findStats(List.of("test"),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(1));
        statService.getStats(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1), List.of("test"), true);
        verify(mockRepository, times(1)).findUniqueStats(List.of("test"),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1));
    }
}