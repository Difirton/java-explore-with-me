package ru.practicum.web.dto.compilation;

import lombok.*;
import ru.practicum.web.dto.event.EventDtoInCollection;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<EventDtoInCollection> events;
}
