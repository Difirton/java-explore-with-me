package ru.practicum.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.practicum.repository.entity.Hit;
import ru.practicum.service.StatService;
import ru.practicum.web.convertor.HitDtoToHitConverter;
import ru.practicum.web.convertor.HitToHitDtoConverter;
import ru.practicum.web.convertor.StatDtoConverter;
import ru.practicum.web.dto.HitDto;
import ru.practicum.web.dto.StatDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatService statService;
    private final HitDtoToHitConverter hitDtoToHitConverter;
    private final HitToHitDtoConverter hitToHitDtoConverter;
    private final StatDtoConverter statDtoConverter;

    @PostMapping(path = "/hit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    HitDto createUser(@Valid @RequestBody HitDto hitDto) {
        Hit newHit = statService.save(hitDtoToHitConverter.convert(hitDto));
        return hitToHitDtoConverter.convert(newHit);
    }

    @GetMapping(path = "/stats", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<StatDto> getHits(@NotNull @RequestParam(name = "start") LocalDateTime start,
                                 @NotNull @RequestParam(name = "end") LocalDateTime end,
                                 @Valid @RequestParam(name = "uris") Optional<List<String>> uris,
                                 @RequestParam(name = "unique") Optional<Boolean> isUnique) {
        return statDtoConverter.convert(statService.getStats(start, end, uris.orElse(List.of()),
                isUnique.orElse(false)));
    }
}
