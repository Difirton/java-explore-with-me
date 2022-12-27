package ru.practicum.web.dto.event;

import lombok.*;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.user.repository.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDtoInCollection {
    private Long id;
    private String annotation;
    private String title;
    private Category category;
    private Integer confirmedRequests;
    private LocalDateTime eventDate;
    private User initiator;
    private Boolean paid;
    private Integer views;
}
