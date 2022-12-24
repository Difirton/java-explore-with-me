package ru.practicum.web.open.event.dto;

import lombok.*;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Location;
import ru.practicum.user.repository.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private String annotation;
    private String description;
    private Category category;
    private LocalDateTime eventDate;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private Integer confirmedRequests;
    private User initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private State state;
    private Integer views;
}
