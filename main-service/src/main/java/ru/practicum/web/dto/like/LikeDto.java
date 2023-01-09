package ru.practicum.web.dto.like;

import lombok.*;
import ru.practicum.web.dto.event.EventDto;
import ru.practicum.web.dto.user.UserDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private Long id;
    private Boolean isLike;
    private EventDto event;
    private UserDto reviewer;
}
