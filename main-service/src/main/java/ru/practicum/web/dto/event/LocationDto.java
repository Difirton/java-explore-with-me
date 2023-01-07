package ru.practicum.web.dto.event;

import lombok.*;

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
