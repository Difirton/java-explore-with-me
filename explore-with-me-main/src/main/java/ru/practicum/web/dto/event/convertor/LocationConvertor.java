package ru.practicum.web.dto.event.convertor;

import org.springframework.stereotype.Component;
import ru.practicum.event.repository.entity.Location;
import ru.practicum.web.dto.event.LocationDto;

import java.math.BigDecimal;

@Component
public class LocationConvertor {

    public LocationDto toLocationDto(Location source) {
        return LocationDto.builder()
                .id(source.getId())
                .lat(source.getLat().doubleValue())
                .lon(source.getLon().doubleValue())
                .build();
    }

    public Location toLocation(LocationDto source) {
        return Location.builder()
                .lat(BigDecimal.valueOf(source.getLat()))
                .lon(BigDecimal.valueOf(source.getLon()))
                .build();
    }
}
