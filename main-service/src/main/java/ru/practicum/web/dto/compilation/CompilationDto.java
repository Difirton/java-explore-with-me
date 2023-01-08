package ru.practicum.web.dto.compilation;

import lombok.*;
import ru.practicum.web.dto.event.EventDtoInCollection;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;
    private Boolean pinned;
    private List<EventDtoInCollection> events;
}
