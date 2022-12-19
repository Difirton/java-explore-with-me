package ru.practicum.web.dto;

import lombok.*;
import ru.practicum.common.validator.IpAddress;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitDto {
    private Long id;
    private String app;
    private String uri;
    @IpAddress
    private String ip;
    private LocalDateTime timestamp;
}
