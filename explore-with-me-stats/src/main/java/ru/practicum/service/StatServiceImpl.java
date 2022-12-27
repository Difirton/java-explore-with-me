package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.repository.StatRepository;
import ru.practicum.repository.entity.EndpointHit;
import ru.practicum.web.dto.StatDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    public void save(EndpointHit endpointHit) {
        List<EndpointHit> hits = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String endpoint : endpointHit.getUri().split("/")) {
            hits.add(EndpointHit.builder()
                            .id(endpointHit.getId())
                            .app(endpointHit.getApp())
                            .ip(endpointHit.getIp())
                            .uri(sb.append("/").append(endpoint).substring(1))
                            .build());
        }
        statRepository.saveAll(hits);
    }

    @Override
    public List<StatDto> getStats(LocalDateTime start, LocalDateTime finish, List<String> uris, boolean isUnique) {
        if (isUnique) {
            return statRepository.findUniqueStats(uris, start, finish);
        } else {
            return statRepository.findStats(uris, start, finish);
        }
    }
}
