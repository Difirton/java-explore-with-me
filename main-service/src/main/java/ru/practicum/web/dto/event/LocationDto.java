package ru.practicum.web.dto.event;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    Long id;

    @NotNull
    Double lat;
    @NotNull
    Double lon;
}
