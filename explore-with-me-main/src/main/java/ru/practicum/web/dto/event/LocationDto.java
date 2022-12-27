package ru.practicum.web.dto.event;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    Long id;
    Double lat;
    Double lon;
}
