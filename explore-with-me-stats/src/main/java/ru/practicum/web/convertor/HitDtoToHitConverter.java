package ru.practicum.web.convertor;

import org.springframework.stereotype.Component;
import ru.practicum.repository.entity.EndpointHit;
import ru.practicum.web.dto.HitDto;
import org.springframework.core.convert.converter.Converter;

@Component
public class HitDtoToHitConverter implements Converter<HitDto, EndpointHit> {

    @Override
    public EndpointHit convert(HitDto source) {
        return EndpointHit.builder()
                .id(source.getId())
                .app(source.getApp())
                .uri(source.getUri())
                .ip(source.getIp())
                .timestamp(source.getTimestamp())
                .build();
    }
}
