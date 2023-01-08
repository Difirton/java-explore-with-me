package ru.practicum.web.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    private User initiator;
    private Boolean paid;
    private Integer views;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime eventDate;
}
