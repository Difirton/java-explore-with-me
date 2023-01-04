package ru.practicum.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.repository.entity.EndpointHit;
import ru.practicum.service.StatService;
import ru.practicum.web.dto.StatDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StatsControllerTest {
    private static final ObjectMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();
    private EndpointHit endpointHit;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatService mockService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
    void testCreateHit() throws Exception {
        mockMvc.perform(post("/hit")
                        .content(jsonMapper.writeValueAsString(endpointHit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mockService, times(1)).save(endpointHit);
    }

    @Test
    @DisplayName("Test get stats expected OK")
    void testGetHits() throws Exception {
        when(mockService.getStats(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(1), null, false))
                .thenReturn(List.of(StatDto.builder()
                        .app("Test")
                        .uri("test")
                        .hits(4L)
                        .build()));

        mockMvc.perform(get("/stats")
                        .param("start", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(formatter))
                        .param("end", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(1)
                                .format(formatter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is("Test")))
                .andExpect(jsonPath("$[0].uri", is("test")))
                .andExpect(jsonPath("$[0].hits", is(4)));
        verify(mockService, times(1)).getStats(
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(1), null, false);
    }
}