package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.repository.HitRepository;
import ru.practicum.repository.entity.Hit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final HitRepository hitRepository;

    @Override
    public Hit save(Hit hit) {
        return null;
    }

    @Override
    public Map<String, Map<String, Long>> getStats(LocalDateTime start, LocalDateTime finish, List<String> uris,
                                                   Boolean isUnique) {
        if (uris.isEmpty()) {
            hitRepository.findAllByTimestampBetween(start, finish).stream()
                    .collect(Collectors.collectingAndThen(Collectors.toList(), List::size));
        }
    }
}
