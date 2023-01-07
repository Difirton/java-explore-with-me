package ru.practicum.web.dto.compilation;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompilationInDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<Long> events;
}