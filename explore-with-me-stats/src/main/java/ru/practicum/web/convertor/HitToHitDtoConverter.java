package ru.practicum.web.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.repository.entity.Hit;
import ru.practicum.web.dto.HitDto;

@Component
public class HitToHitDtoConverter implements Converter<Hit, HitDto> {
    @Override
    public HitDto convert(Hit source) {
        return HitDto.builder()
                .id(source.getId())
                .app(source.getApp())
                .uri(source.getUri())
                .ip(source.getIp())
                .timestamp(source.getTimestamp())
                .build();
    }
}
