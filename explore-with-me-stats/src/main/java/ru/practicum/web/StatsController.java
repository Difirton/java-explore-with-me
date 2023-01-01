package ru.practicum.web;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.StatService;
import ru.practicum.web.convertor.HitDtoToHitConverter;
import ru.practicum.web.dto.HitDto;
import ru.practicum.web.dto.StatDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatService statService;
    private final HitDtoToHitConverter hitDtoToHitConverter;

    private final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.OK)
    void createHit(@Valid @RequestBody HitDto hitDto) {
        System.out.println(hitDto);
        statService.save(hitDtoToHitConverter.convert(hitDto));
    }

    @GetMapping(path = "/stats")
    @ResponseStatus(HttpStatus.OK)
    List<StatDto> getHits(@DateTimeFormat(pattern = DATE_TIME_PATTERN) @RequestParam LocalDateTime start,
                          @DateTimeFormat(pattern = DATE_TIME_PATTERN) @RequestParam LocalDateTime end,
                          @RequestParam(required = false) List<String> uris,
                          @RequestParam(name = "unique") Optional<Boolean> isUnique) {
        return statService.getStats(start, end, uris, isUnique.orElse(false));
    }
}
